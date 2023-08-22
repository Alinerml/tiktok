package com.tiktok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.FriendUser;
import com.tiktok.bean.User;
import com.tiktok.bean.UserFollow;
import com.tiktok.bean.dto.RelationActionDto;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.common.contants.enums.ExceptionEnum;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.mapper.UserFollowMapper;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IFriendUserService;
import com.tiktok.service.IUserFollowService;
import com.tiktok.service.IUserService;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    IFriendUserService friendUserService;
    

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
        String userId = JwtUtil.getUserIdFromToken(token); //当前用户id
        String toUserId = relationActionDto.getTo_user_id(); //对方用户id
        String actionType = relationActionDto.getAction_type();
        if (JwtUtil.validateToken(token)) {
            User user = this.getById(userId);
            User toUser = this.getById(toUserId);

            if (actionType.equals("1")){ //关注
                //当前用户的一些操作
                user.setFollowCount(user.getFollowCount()+1); //关注数量+1

                //对方用户的一些操作
                toUser.setIsFollow(true);
                toUser.setFollowCount(toUser.getFollowerCount()+1);//粉丝数量+1
                //新增UserFollow
                UserFollow userFollow = new UserFollow();
                userFollow.setUserId(userId);
                userFollow.setFollowUserId(toUserId);
                userFollowService.save(userFollow);

                //新增FriendUser -信息字段暂滞空
                FriendUser friendUser = new FriendUser();
                BeanUtils.copyProperties(toUser,friendUser);
                friendUserService.save(friendUser);
            }
            if (actionType.equals("2")){ //取消关注
                //当前用户的一些操作
                user.setFollowCount(user.getFollowCount()-1); //关注数量+1

                //对方用户的一些操作
                toUser.setIsFollow(false);
                toUser.setFollowCount(toUser.getFollowerCount()-1);//粉丝数量-1
                //删除UserFollow
                MPJLambdaWrapper<UserFollow> wrapper = new MPJLambdaWrapper<UserFollow>()
                        .selectAll(UserFollow.class)
                        .eq(UserFollow::getFollowUserId,toUserId)
                        .eq(UserFollow::getUserId,userId);
                UserFollow userFollow = userFollowMapper.selectJoinOne(UserFollow.class, wrapper);
                userFollowService.removeById(userFollow);
                //删除FriendUser
                FriendUser friendUser = friendUserService.getById(toUserId);
                friendUserService.removeById(friendUser);
            }
            this.updateById(toUser);
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

    @Override
    public User queryByName(String name){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getName,name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
