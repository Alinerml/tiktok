package com.tiktok.mapper;

import java.util.List;

import com.github.yulichang.base.MPJBaseMapper;
import com.tiktok.bean.UserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: user_follow
 * @Author: jeecg-boot
 * @Date:   2023-08-09
 * @Version: V1.0
 */
@Mapper
public interface UserFollowMapper extends MPJBaseMapper<UserFollow> {

}
