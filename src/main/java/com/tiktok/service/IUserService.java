package com.tiktok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.UserInfo;

import java.util.HashMap;

public interface IUserService extends IService<UserInfo> {
    /**
     * 存储账号密码 并返回id值
     * @param username
     * @param password
     * @return
     */
    Integer register(String username, String password);

    /**
     * 做完登录判断后返回id值
     * @param username
     * @param password
     * @return
     */
    Integer login(String username, String password);

    /**
     * 用户信息接口中判断token 包装user
     * @param id
     * @param token
     * @return
     */
    HashMap<String, Object> getUserInfo(Integer id, String token);
}
