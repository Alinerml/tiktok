package com.tiktok.service;

import com.tiktok.bean.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.Video;
import com.tiktok.bean.dto.CommentDto;
import com.tiktok.bean.dto.FavoriteListDto;
import com.tiktok.bean.dto.VideoCommentsDto;

import java.util.List;

/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
public interface ICommentService extends IService<Comment> {

    Comment comment(CommentDto commentDto);

    List<Comment> videoComments(VideoCommentsDto videoCommentsDto);
}
