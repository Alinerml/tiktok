package com.tiktok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.bean.vo.UserLoginVo;

public interface IUserInfoService extends IService<UserInfo> {
    UserLoginVo register(UserInfoDto userInfoDto);

    UserLoginVo login(UserInfoDto userInfoDto);

    UserInfo getUserByUserName(String userName);

    void logout(UserIdAndTokenDto userIdAndTokenDto);
}
