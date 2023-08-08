package com.tiktok.service.impl;

import com.tiktok.bean.VideoComment;
import com.tiktok.mapper.VideoCommentMapper;
import com.tiktok.service.IVideoCommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: video_comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Service
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements IVideoCommentService {

}
