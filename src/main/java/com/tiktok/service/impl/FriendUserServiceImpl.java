package com.tiktok.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiktok.bean.FriendUser;
import com.tiktok.bean.dto.UserIdAndTokenDto;
import com.tiktok.common.common.contants.enums.ExceptionEnum;
import com.tiktok.common.common.exception.TiktokException;
import com.tiktok.common.common.utils.JwtUtil;
import com.tiktok.mapper.FriendUserMapper;
import com.tiktok.service.IFriendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: friend_user
 * @Author: jeecg-boot
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Service
public class FriendUserServiceImpl extends ServiceImpl<FriendUserMapper, FriendUser> implements IFriendUserService {
    @Autowired
    FriendUserMapper friendUserMapper;
    @Override
    public List<FriendUser> friendList(UserIdAndTokenDto userIdAndTokenDto) {
        String token = userIdAndTokenDto.getToken();
        //直接把friend_user中全部数据查出来
        if (!JwtUtil.validateToken(token)) {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
        List<FriendUser> friendUsers = friendUserMapper.getAll();

        return friendUsers;
    }
}
