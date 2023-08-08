package com.tiktok.mapper;

import java.util.List;

import com.tiktok.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
