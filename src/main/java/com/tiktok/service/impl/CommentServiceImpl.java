package com.tiktok.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.Comment;
import com.tiktok.bean.Video;
import com.tiktok.bean.VideoComment;
import com.tiktok.bean.dto.CommentDto;
import com.tiktok.bean.dto.VideoCommentsDto;
import com.tiktok.common.contants.enums.ExceptionEnum;
import com.tiktok.common.exception.TiktokException;
import com.tiktok.common.utils.DateUtils;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.mapper.CommentMapper;
import com.tiktok.mapper.VideoCommentMapper;
import com.tiktok.service.ICommentService;
import com.tiktok.service.IVideoCommentService;
import com.tiktok.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    IVideoService videoService;
    @Autowired
    IVideoCommentService videoCommentService;
    @Autowired
    VideoCommentMapper videoCommentMapper;
    @Override
    public Comment comment(CommentDto commentDto) {
        //自动获取登录用户信息 -解析token获得...
        String token = commentDto.getToken();
        String userIdFromToken = JwtUtil.getUserIdFromToken(token);
        if (JwtUtil.validateToken(token)) {


            String videoId = commentDto.getVideo_id();
            Integer actionType = commentDto.getAction_type();
            String commentText = commentDto.getComment_text();
            String commentId = commentDto.getComment_id();

            //获取当前日期
            String createTime = DateUtils.getCurrentDate();

            Video video = videoService.getById(videoId);
            //发布评论 -新增+返回+video中数量+ + 视频评论中间表的新增
            if (actionType == 1) {
                video.setCommentCount(video.getCommentCount() + 1);
                videoService.updateById(video);

                Comment comment = new Comment();
                comment.setUserId(userIdFromToken);
                comment.setContent(commentText);
                comment.setCreateTime(createTime);
                this.save(comment);

                //中间表的新增
                VideoComment videoComment = new VideoComment();
                videoComment.setCommentId(commentId);
                videoComment.setVideoId(videoId);
                videoCommentService.save(videoComment);

                return comment;
            }

            //删除评论 -删除+修改video中总数-
            if (actionType == 2) {
                this.removeById(commentId);
                video.setCommentCount(video.getCommentCount() - 1);
                videoService.updateById(video);
                return null;
            }
            return null;
        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }


    @Override
    public List<Comment> videoComments(VideoCommentsDto videoCommentsDto) {
        //以video_id去中间表中查出全部的comment_ids
        //1.鉴权token...
        String token = videoCommentsDto.getToken();
        if (JwtUtil.validateToken(token)) {
            String videoId = videoCommentsDto.getVideo_id();
            VideoComment videoComment = new VideoComment();
            MPJLambdaWrapper<VideoComment> wrapper = new MPJLambdaWrapper<VideoComment>()
                    .eq(VideoComment::getVideoId, videoId);
            List<VideoComment> videoComments = videoCommentMapper.selectJoinList(VideoComment.class, wrapper);
            List<String> commentIdList = videoComments.stream()
                    .map(VideoComment::getCommentId)
                    .collect(Collectors.toList());
            //comment_ids去查出对应的List<Comment>
            List<Comment> comments = this.listByIds(commentIdList);
            return comments;
        } else {
            throw new TiktokException(ExceptionEnum.TOKEN_FAIL);
        }
    }
}
