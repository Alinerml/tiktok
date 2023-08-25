package com.tiktok.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.FriendUser;
import com.tiktok.bean.Message;
import com.tiktok.bean.User;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.bean.vo.UserLoginVo;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.common.utils.RedisUtil_db0;
import com.tiktok.mapper.FriendUserMapper;
import com.tiktok.mapper.MessageMapper;
import com.tiktok.mapper.UserInfoMapper;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IFriendUserService;
import com.tiktok.service.IUserFollowService;
import com.tiktok.service.IUserInfoService;
import com.tiktok.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    IUserService userService;
    @Autowired
    IUserFollowService userFollowService;
    @Autowired
    IFriendUserService friendUserService;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FriendUserMapper friendUserMapper;
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    private RedisUtil_db0 redisUtil;
    private static final String SALT = "JYU";

    @Override
    public UserLoginVo register(UserInfoDto userInfoDto) {
        //不仅新增userInfo，同时新增user
        String username = userInfoDto.getUsername();
        String password = userInfoDto.getPassword();

        //1.先save userInfo，要自己创造uuid-交给注解解决
        UserInfo userInfo = new UserInfo();

        //username需要保证唯一 -去 UserInfo 中查询
        UserInfo userInfo1 = this.getUserByUserName(username);
        if (userInfo1 != null){
            throw new TiktokException("该用户名已存在，请选择一个不同的用户名");
        }
        userInfo.setUsername(username);
        userInfo.setPassword(new BCryptPasswordEncoder().encode(password));
        // 异步生成令牌和保存用户
//        CompletableFuture<String> tokenFuture = generateToken(userInfo);
//        CompletableFuture<User> saveUserFuture = saveUserInfo(userInfo);
        //需要异步编程，等待save后得到userInfoId，再进行token生成和user save
        this.save(userInfo);

        //2.save user，保证同id，除id和name外其他字段默认值
        String userInfoId = userInfo.getId(); //到底会不会在save前getId成功-不会

        String generateToken = JwtUtil.generateToken(userInfoId);
        userInfo.setToken(generateToken);
        this.updateById(userInfo); //*可以升级为异步解决

        String userInfoUsername = userInfo.getUsername();
        User user = new User();
        user.setId(userInfoId);
        user.setName(userInfoUsername);

        //保证默认值
        user.setFollowCount(0);
        user.setFollowerCount(0);
        user.setIsFollow(false);
        user.setAvatar(null);
        user.setBackgroundImage(null);
        user.setSignature("简短的介绍一下自己吧~");
        user.setTotalFavorited(0);
        user.setWorkCount(0);
        user.setFavoriteCount(0);

        //判断原来是否已经存在该用户，用username唯一性
        User user1 = userService.queryByName(userInfoUsername);
        if (user1 == null){
            userService.save(user);
        }

        //包装返回类
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser_id(userInfoId);
        userLoginVo.setToken(generateToken);
        return userLoginVo;
    }

    //异步保存userInfo
//    public CompletableFuture<UserInfo> saveUserInfo(UserInfo userInfo){
//        return CompletableFuture.supplyAsync(() -> {
//            generateToken(userInfo)
//            userInfo.setToken(generateToken);
//            this.save(userInfo);
//
//            return userInfo;
//        });
//    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserLoginVo login(UserInfoDto userInfoDto) {
        //用name去查询对应用户
        String DtoUsername = userInfoDto.getUsername();
        String DtoPassword = userInfoDto.getPassword();
        UserInfo userInfo = this.getUserByUserName(DtoUsername);

        //密码判断
        String userInfoPassword = userInfo.getPassword();
        String userInfoId = userInfo.getId();
        if(!passwordEncoder.matches(DtoPassword, userInfoPassword)) {
            throw new TiktokException("输入密码错误，请再试一次！");
        }

        //包装返回Vo类
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser_id(userInfoId);
        String generateToken = JwtUtil.generateToken(userInfoId);
        userLoginVo.setToken(generateToken);

//        //保存token到userinfo表
//        userInfo.setToken(generateToken);
//        //bug：id也要再修改，登录新生成的token，导致token解析出的id和原注册的不同
//        String userIdFromToken = JwtUtil.getUserIdFromToken(generateToken);
//        userInfo.setId(userIdFromToken);
//        userInfoMapper.updateById(userInfo);

        //存到redis中（键-userId 值-jwt）
        redisUtil.set("login" + userInfoId, generateToken,60 * 60 * 6); //6小时

        //根据user_follow中对应用户数据，重新计算好友列表FriendUser中数据
        //0.先删了FriendUser中全部数据
        friendUserMapper.deleteAll();
        //1.根据name去查id
        User user = userService.queryByName(DtoUsername);
        String userId = user.getId();
        //2.去user_follow中查询所有好友ids
        List<String> followUserIds = userFollowService.queryFollowUserIds(userId);
        //3.先去创建对应的所有FriendUser
        for (String id : followUserIds){
            User user1 = userService.getById(id);
            FriendUser friendUser = new FriendUser();
            BeanUtils.copyProperties(user1,friendUser);
            friendUserService.save(friendUser);
            //4.[查看和该好友的最新聊天消息]根据message去完善每个FriendUser followUserIds去message中查询
            //新消息存在两种情况1.好友发你消息 2.你发好友消息，所以要以userId-followUserId或followUserId-userId组合去查询最新记录
            //4-1.先以userId-followUserId去查询最新消息
            LambdaQueryWrapper<Message> wrapper1 = new LambdaQueryWrapper<Message>()
                    .eq(Message::getFromUserId,userId)
                    .eq(Message::getToUserId,id)
                    .orderByDesc(Message::getCreateTime);//最新-降序排序
            List<Message> messagesIU = messageMapper.selectList(wrapper1);
            Message messageIU = null;
            if (messagesIU.size() != 0) {
                messageIU = messagesIU.get(0);//获取最新的
            }
            //4-2.再以followUserId-userId去查询
            LambdaQueryWrapper<Message> wrapper2 = new LambdaQueryWrapper<Message>()
                    .eq(Message::getFromUserId,id)
                    .eq(Message::getToUserId,userId)
                    .orderByDesc(Message::getCreateTime);
            List<Message> messagesUI = messageMapper.selectList(wrapper2);
            Message messageUI = null;
            if (messagesUI.size() != 0){
                messageUI = messagesUI.get(0);
            }
            //若其中一者为空，则字段message直接设置为不为空的内容
            if (messageIU == null && messageUI != null){
                friendUser.setMessage(messageUI.getContent());
                friendUser.setMsgType(0);
            }
            if (messageUI == null && messageIU != null){
                friendUser.setMessage(messageIU.getContent());
                friendUser.setMsgType(1);
            }
            if (messageUI != null && messageIU != null){
                //4-3.比较后取两者最新的消息
                if (messageIU.getCreateTime().compareTo(messageUI.getCreateTime()) > 0) {
                    // messageIU 的创建时间较新
                    friendUser.setMessage(messageIU.getContent());
                    friendUser.setMsgType(1);

                } else if (messageIU.getCreateTime().compareTo(messageUI.getCreateTime()) < 0) {
                    // messageUI 的创建时间较新
                    friendUser.setMessage(messageUI.getContent());
                    friendUser.setMsgType(0);

                } else {
                    // 创建时间相同-自定义为以我发的为准
                    friendUser.setMessage(messageIU.getContent());
                    friendUser.setMsgType(1);
                }
            }
            friendUserService.updateById(friendUser);
        }

        return userLoginVo;
    }

    @Override
    public UserInfo getUserByUserName(String userName) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername,userName);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo;
    }

    @Override
    public void logout(UserIdAndTokenDto userIdAndTokenDto) {
        String token = userIdAndTokenDto.getToken();
        String userId = userIdAndTokenDto.getUser_id();
        String tokenInRedis = (String) redisUtil.get("login" + userId);
        if(tokenInRedis != null && token.equals(tokenInRedis)){
            //删除redis中的token
            redisUtil.del("login"+ userId);
        }
    }
}
