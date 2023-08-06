package com.tiktok.mapper;

import java.util.List;

import com.github.yulichang.base.MPJBaseMapper;
import com.tiktok.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

}
