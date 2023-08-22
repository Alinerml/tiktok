package com.tiktok.mapper;

import java.util.List;

import com.tiktok.bean.FriendUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: friend_user
 * @Author: jeecg-boot
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Mapper
public interface FriendUserMapper extends BaseMapper<FriendUser> {

    /**
     * 删除全部数据
     */
    void deleteAll();

    /**
     * 获取全部数据
     */
    List<FriendUser> getAll();
}
