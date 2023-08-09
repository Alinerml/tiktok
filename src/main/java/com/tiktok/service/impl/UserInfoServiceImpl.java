package com.tiktok.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.User;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.bean.vo.UserLoginVo;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.mapper.UserInfoMapper;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IUserInfoService;
import com.tiktok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    IUserService userService;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserMapper userMapper;

    private static final String SALT = "JYU";

    @Override
    public UserLoginVo register(UserInfoDto userInfoDto) {
        //不仅新增userInfo，同时新增user
        String username = userInfoDto.getUsername();
        String password = userInfoDto.getPassword();

        //1.先save userInfo，要自己创造uuid-交给注解解决
        UserInfo userInfo = new UserInfo();

        //username需要保证唯一 -去 UserInfo 中查询
        MPJLambdaWrapper<UserInfo> wrapper = new MPJLambdaWrapper<UserInfo>()
                .selectAll(UserInfo.class)
                .eq(UserInfo::getUsername,username);
        UserInfo one = userInfoMapper.selectJoinOne(UserInfo.class,wrapper);
        if (one != null){
            throw new TiktokException("该用户名已存在，请选择一个不同的用户名");
        }
        userInfo.setUsername(username);

        //密码作加密
        userInfo.setPassword(DigestUtil.md5Hex(password + SALT));

        // 异步生成令牌和保存用户
//        CompletableFuture<String> tokenFuture = generateToken(userInfo);
//        CompletableFuture<User> saveUserFuture = saveUserInfo(userInfo);
        //需要异步编程，等待save后得到userInfoId，再进行token生成和user save
        this.save(userInfo);

        //2.save user，保证同id，除id和name外其他字段默认值
        String userInfoId = userInfo.getId(); //到底会不会在save前getId成功-不会

        String generateToken = JwtUtil.generateToken(userInfoId);
        userInfo.setToken(generateToken);
        this.updateById(userInfo); //可以升级为异步解决

        String userInfoUsername = userInfo.getUsername();
        User user = new User();
        user.setId(userInfoId);
        user.setName(userInfoUsername);

        //保证默认值
        user.setFollowCount(0);
        user.setFollowerCount(0);
        user.setIsFollow(0);
        user.setAvatar(null);
        user.setBackgroundImage(null);
        user.setSignature("简短的介绍一下自己吧~");
        user.setTotalFavorited(0);
        user.setWorkCount(0);
        user.setFavoriteCount(0);

        //判断原来是否已经存在该用户，用username唯一性
        MPJLambdaWrapper<User> wrapper1 = new MPJLambdaWrapper<User>()
                        .selectAll(User.class)
                        .eq(User::getName,userInfoUsername);
        User one1 = userMapper.selectJoinOne(User.class, wrapper1);

        if (one1 == null){
            userService.save(user);
        }

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUser_id(userInfoId);
        userLoginVo.setToken(generateToken);

        return userLoginVo;
    }

    //异步生成token
    public CompletableFuture<String> generateToken(String userInfoId){
        return CompletableFuture.supplyAsync(() -> {
            String generateToken = JwtUtil.generateToken(userInfoId);
            return generateToken;
        });
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


    @Override
    public UserLoginVo login(UserInfoDto userInfoDto) {
        //用name去查询对应用户，然后验证password
        String DtoUsername = userInfoDto.getUsername();
        String DtoPassword = userInfoDto.getPassword();
        MPJLambdaWrapper<UserInfo> wrapper = new MPJLambdaWrapper<UserInfo>()
                .selectAll(UserInfo.class)
                .eq(UserInfo::getUsername,DtoUsername);
        UserInfo userInfo = userInfoMapper.selectJoinOne(UserInfo.class,wrapper);

        String userInfoPassword = userInfo.getPassword();
        String userInfoId = userInfo.getId();

        String md5Hex = DigestUtil.md5Hex(DtoPassword + SALT);

        if (md5Hex.equals(userInfoPassword)) {
            UserLoginVo userLoginVo = new UserLoginVo();
            userLoginVo.setUser_id(userInfoId);

            String generateToken = JwtUtil.generateToken(userInfoId);
            userLoginVo.setToken(generateToken);

            //保存token到userinfo表
            userInfo.setToken(generateToken);
            userInfoMapper.updateById(userInfo);

            return userLoginVo;
        } else {
            throw new TiktokException("输入密码错误，请再试一次！");
        }


    }
}
