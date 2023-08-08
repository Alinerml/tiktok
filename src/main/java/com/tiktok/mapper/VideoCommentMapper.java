package com.tiktok.mapper;

import java.util.List;

import com.github.yulichang.base.MPJBaseMapper;
import com.tiktok.bean.VideoComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: video_comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Mapper
public interface VideoCommentMapper extends MPJBaseMapper<VideoComment> {

}
