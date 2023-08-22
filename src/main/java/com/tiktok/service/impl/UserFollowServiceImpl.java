package com.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tiktok.bean.UserFollow;
import com.tiktok.mapper.UserFollowMapper;
import com.tiktok.service.IUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: user_follow
 * @Author: jeecg-boot
 * @Date:   2023-08-09
 * @Version: V1.0
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {
    @Autowired
    UserFollowMapper userFollowMapper;

    @Override
    public List<String> queryFollowUserIds(String userId){
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<UserFollow>()
                .eq(UserFollow::getUserId,userId);
        List<UserFollow> userFollows = userFollowMapper.selectList(wrapper);
        List<String> ids = userFollows.stream()
                .map(UserFollow::getFollowUserId)
                .collect(Collectors.toList());
        return ids;
    }

}
