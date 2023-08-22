package com.tiktok.controller;

import com.tiktok.bean.FriendUser;
import com.tiktok.bean.User;
import com.tiktok.bean.dto.RelationActionDto;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IFriendUserService;
import com.tiktok.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="relation")
@RestController
@RequestMapping("/relation")
@Slf4j
public class RelationController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IFriendUserService friendUserService;

    @ApiOperation(value="关系操作", notes="关系操作")
    @PostMapping(value = "/action")
    public Result<String> action(@RequestBody RelationActionDto relationActionDto) {
        userService.action(relationActionDto);
        return Result.OK("操作成功！");
    }

    @ApiOperation(value="用户关注列表", notes="用户关注列表")
    @PostMapping(value = "/follow/list")
    public Result followList(@RequestBody UserIdAndTokenDto userIdAndTokenDto) {
        List<User> users = userService.followList(userIdAndTokenDto);
        return Result.OK("操作成功！",users);
    }

    @ApiOperation(value="用户粉丝列表", notes="用户粉丝列表")
    @PostMapping(value = "/follower/list")
    public Result followerList(@RequestBody UserIdAndTokenDto userIdAndTokenDto) {
        List<User> users = userService.followerList(userIdAndTokenDto);
        return Result.OK("操作成功！",users);
    }


    @ApiOperation(value="用户好友列表", notes="用户好友列表")
    @PostMapping(value = "/friend/list/")
    public Result friendList(@RequestBody UserIdAndTokenDto userIdAndTokenDto) {
        List<FriendUser> friendUsers = friendUserService.friendList(userIdAndTokenDto);
        return Result.OK("操作成功！",friendUsers);
    }
}
