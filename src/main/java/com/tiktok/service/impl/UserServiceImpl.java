package com.tiktok.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.User;
import com.tiktok.bean.UserFollow;
import com.tiktok.bean.dto.RelationActionDto;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.common.contants.enums.ExceptionEnum;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.mapper.UserFollowMapper;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IUserFollowService;
import com.tiktok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserFollowMapper userFollowMapper;

    @Autowired
    IUserFollowService userFollowService;
    

    @Override
    public User queryById(UserIdAndTokenDto userIdAndTokenDto) {
        //鉴权token
        String token = userIdAndTokenDto.getToken();
        String userId = userIdAndTokenDto.getUser_id();
        if (JwtUtil.validateToken(token)) { //err猜测：SECRET_KEY一开始是可变的 -ok
            //查询user并且返回
            User user = this.getById(userId);
            return user;
        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }

    @Override
    public void action(RelationActionDto relationActionDto) {
        String token = relationActionDto.getToken();
        String toUserId = relationActionDto.getTo_user_id();
        String actionType = relationActionDto.getAction_type();
        if (JwtUtil.validateToken(token)) {
            User user = this.getById(toUserId);
//            user.setIsFollow(!user.getIsFollow()); //直接取反 -不够精确

            String userId = JwtUtil.getUserIdFromToken(token);
            if (actionType.equals("1")){
                user.setIsFollow(true);
                user.setFollowCount(user.getFollowCount()+1);
                //新增UserFollow
                UserFollow userFollow = new UserFollow();
                userFollow.setUserId(userId);
                userFollow.setFollowUserId(toUserId);
                userFollowService.save(userFollow);
            }
            if (actionType.equals("2")){
                user.setIsFollow(false);
                user.setFollowCount(user.getFollowCount()-1);
                //删除UserFollow
                MPJLambdaWrapper<UserFollow> wrapper = new MPJLambdaWrapper<UserFollow>()
                        .selectAll(UserFollow.class)
                        .eq(UserFollow::getFollowUserId,toUserId)
                        .eq(UserFollow::getUserId,userId);
                UserFollow userFollow = userFollowMapper.selectJoinOne(UserFollow.class, wrapper);
                userFollowService.removeById(userFollow);
            }
            this.updateById(user);


        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }

    @Override
    public List<User> followList(UserIdAndTokenDto userIdAndTokenDto) {
        String token = userIdAndTokenDto.getToken();
        String userId = userIdAndTokenDto.getUser_id();

        if (JwtUtil.validateToken(token)) {
            //去查询UserFollow表中全部关注的ids
            MPJLambdaWrapper<UserFollow> wrapper = new MPJLambdaWrapper<UserFollow>()
                    .selectAll(UserFollow.class)
                    .eq(UserFollow::getUserId,userId);
            List<UserFollow> userFollows = userFollowMapper.selectJoinList(UserFollow.class, wrapper);
            List<String> ids = userFollows.stream()
                    .map(UserFollow::getFollowUserId)
                    .collect(Collectors.toList());
            //对ids转换为users
            List<User> users = this.listByIds(ids);
            return users;
        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }

    @Override
    public List<User> followerList(UserIdAndTokenDto userIdAndTokenDto) {
        String token = userIdAndTokenDto.getToken();
        String userId = userIdAndTokenDto.getUser_id();

        if (JwtUtil.validateToken(token)) {
            //去查询UserFollow表中全部被关注的ids
            MPJLambdaWrapper<UserFollow> wrapper = new MPJLambdaWrapper<UserFollow>()
                    .selectAll(UserFollow.class)
                    .eq(UserFollow::getFollowUserId,userId);
            List<UserFollow> userFollows = userFollowMapper.selectJoinList(UserFollow.class, wrapper);
            List<String> ids = userFollows.stream()
                    .map(UserFollow::getUserId)
                    .collect(Collectors.toList());
            //对ids转换为users
            List<User> users = this.listByIds(ids);
            return users;

        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }

    }
}
