package com.tiktok.service;

import com.tiktok.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.dto.RelationActionDto;
import com.tiktok.bean.dto.UserIdAndTokenDto;

import java.util.List;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
public interface IUserService extends IService<User> {

    User queryById(UserIdAndTokenDto userIdAndTokenDto);

    /**
     * 登录用户对其他用户进行关注或取消关注。
     * @param relationActionDto
     */
    void action(RelationActionDto relationActionDto);

    /**
     * 用户关注列表
     * @param userIdAndTokenDto
     * @return
     */
    List<User> followList(UserIdAndTokenDto userIdAndTokenDto);

    List<User> followerList(UserIdAndTokenDto userIdAndTokenDto);
}
