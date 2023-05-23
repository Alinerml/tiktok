package com.tiktok.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiktok.bean.UserData;
import com.tiktok.bean.UserFile;
import com.tiktok.bean.UserFollow;
import com.tiktok.bean.UserInfo;
import com.tiktok.config.LoginException;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IUserService;
import com.tiktok.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

//service层的实现类
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private static final String SALT = "JYU";

    //实现效果还有插入语句 所以放service没问题
    @Override
    public Integer register(String username, String password) {
        if (StrUtil.isBlankIfStr(username) || StrUtil.isBlankIfStr(password)) {
            //
        }
        UserInfo user = new UserInfo();
        user.setUsername(username);
        // 密码加密
        user.setPassword(DigestUtil.md5Hex(password + SALT));
        this.save(user); //大概同insert功能
        return user.getId();
    }

    @Override
    public Integer login(String username, String password) {
        UserInfo user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new LoginException("账号不存在");
        }

        String digestPassword = DigestUtil.md5Hex(password + SALT);
        if (!digestPassword.equals(user.getPassword())) {
            throw new LoginException("账号或者密码错误");
        }
        return user.getId();
    }

    @Override
    public HashMap<String, Object> getUserInfo(Integer id, String token) {
        //校验token
        try {
            JwtUtil.verifyToken(token);
        } catch (Exception e) {
            throw new RuntimeException("token校验失败");
        }

        //根据id查询四个表的别的字段
        UserInfo userInfo = userMapper.selectByIdFromUserInfo(id);
        UserData userData = userMapper.selectByIdFromUserData(id);
        UserFile userFile = userMapper.selectByIdFromUserFile(id);
        UserFollow userFollow = userMapper.selectByIdFromUserFollow(id);

        HashMap<String, Object> user = new HashMap<String, Object>();
        user.put("id",userInfo.getId());user.put("name",userInfo.getUsername());
        user.put("follow_count",userFollow.getFollow_count());user.put("follower_count",userFollow.getFollower_count());user.put("is_follow",userFollow.is_follow());
        user.put("avatar",userFile.getAvatar());user.put("background_image",userFile.getBackground_image());user.put("signature",userFile.getSignature());
        user.put("total_favorited",userData.getTotal_favorited());user.put("work_count",userData.getWork_count());user.put("favorite_count",userData.getFavorite_count());

        return user;
    }
}
