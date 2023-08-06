package com.tiktok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserInfoDto;

public interface IUserInfoService extends IService<UserInfo> {
    String register(UserInfoDto userInfoDto);

    String login(UserInfoDto userInfoDto);
}
