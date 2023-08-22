package com.tiktok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.FriendUser;
import com.tiktok.bean.User;
import com.tiktok.bean.dto.UserIdAndTokenDto;

import java.util.List;

/**
 * @Description: friend_user
 * @Author: jeecg-boot
 * @Date:   2023-08-15
 * @Version: V1.0
 */
public interface IFriendUserService extends IService<FriendUser> {

    List<FriendUser> friendList(UserIdAndTokenDto userIdAndTokenDto);
}
