package com.tiktok.service.impl;

import com.tiktok.bean.UserFollow;
import com.tiktok.mapper.UserFollowMapper;
import com.tiktok.service.IUserFollowService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: user_follow
 * @Author: jeecg-boot
 * @Date:   2023-08-09
 * @Version: V1.0
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {

}
