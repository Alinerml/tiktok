package com.tiktok.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.User;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.mapper.UserInfoMapper;
import com.tiktok.service.IUserInfoService;
import com.tiktok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    IUserService userService;

    @Autowired
    UserInfoMapper userInfoMapper;

    private static final String SALT = "JYU";

    @Override
    public String register(UserInfoDto userInfoDto) {
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
        this.save(userInfo);

        //2.save user，保证同id，除id和name外其他字段默认值
        String userInfo_id = userInfo.getId();
        String userInfo_username = userInfo.getUsername();
        User user = new User();
        user.setId(userInfo_id);
        user.setName(userInfo_username);

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

        userService.save(user);

        return userInfo_id;
    }


    @Override
    public String login(UserInfoDto userInfoDto) {
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

//        currentUser = userInfo; //设置为全局对象，方便其他模块获取当前用户信息

        if (md5Hex.equals(userInfoPassword)) {
            return userInfoId;
        } else {
            throw new TiktokException("输入密码错误，请再试一次！");
        }


    }
}
