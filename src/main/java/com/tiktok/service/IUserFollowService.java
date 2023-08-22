package com.tiktok.service;

import com.tiktok.bean.UserFollow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: user_follow
 * @Author: jeecg-boot
 * @Date:   2023-08-09
 * @Version: V1.0
 */
public interface IUserFollowService extends IService<UserFollow> {

    List<String> queryFollowUserIds(String userId);
}
