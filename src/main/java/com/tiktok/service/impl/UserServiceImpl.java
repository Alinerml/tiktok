package com.tiktok.service.impl;

import com.tiktok.bean.User;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.common.contants.enums.ExceptionEnum;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User queryById(UserIdAndTokenDto userIdAndTokenDto) {
        //鉴权token
        String token = userIdAndTokenDto.getToken();
        String userId = userIdAndTokenDto.getUser_id();
        if (JwtUtil.validateToken(token)) { //err猜测：SECRET_KEY一开始是可变的
            //查询user并且返回
            User user = this.getById(userId);
            return user;
        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }
}
