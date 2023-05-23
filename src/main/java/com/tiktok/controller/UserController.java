package com.tiktok.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.tiktok.bean.UserData;
import com.tiktok.bean.UserFile;
import com.tiktok.bean.UserFollow;
import com.tiktok.bean.UserInfo;
import com.tiktok.common.BaseResult;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IUserService;
import com.tiktok.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    //    private RegisterResult result;
    private static final String SALT = "JYU";

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register/")
    public BaseResult register(@RequestParam String username, @RequestParam String password) {
        try {
            Integer userId = userService.register(username, password);
//            result.setUserId(userId);
//            result.setStatusMessage(null);
//            result.setStatusCode(0);
//            result.setToken(JwtUtil.create());
            return BaseResult.ok().put("user_id", userId).put("token", JwtUtil.create());

        } catch (Exception e) {
//            result.setStatusCode(-1);
//            result.setStatusMessage(e.getMessage());
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login/")
    public BaseResult login(@RequestParam String username, @RequestParam String password) {
        try {
//            Integer userId = userService.login(username, password);
//            return BaseResult.ok().put("userId", userId).put("token", JwtUtil.create());
            return BaseResult.ok().put("username",username).put("password",password);
        } catch (Exception e) {
            return BaseResult.error(403, e.getMessage());
        }
    }

    /**
     * 用户信息
     * @param user_id
     * @param token
     * @return
     */
    @GetMapping("/") //get请求:不会修改、增加数据，不会影响资源的内容
    public BaseResult getUserInfo(@RequestParam Integer user_id, @RequestParam String token){
        HashMap<String, Object> user = userService.getUserInfo(user_id, token);

        try {
            return BaseResult.ok().put("user",user);
        } catch (Exception e) {
            return BaseResult.error(403, e.getMessage());
        }
    }

}
