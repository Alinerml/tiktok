package com.tiktok.controller;

import com.tiktok.bean.Comment;
import com.tiktok.bean.Video;
import com.tiktok.bean.dto.CommentDto;
import com.tiktok.bean.dto.FavoriteListDto;
import com.tiktok.bean.dto.VideoCommentsDto;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.ICommentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Api(tags="comment")
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
	@Autowired
	private ICommentService commentService;

	/**
	 * 评论操作
	 * @return
	 */
	@ApiOperation(value="评论操作", notes="评论操作")
	@PostMapping(value = "/action")
	public Result comment(@RequestBody CommentDto commentDto) {
		Comment comment = commentService.comment(commentDto);
		return Result.OK("评论操作成功！",comment);
	}

	/**
	 * 视频评论列表
	 * @return
	 */
	@ApiOperation(value="视频评论列表", notes="视频评论列表")
	@PostMapping(value = "/list")
	public Result videoComments(@RequestBody VideoCommentsDto videoCommentsDto) {
		List<Comment> comments = commentService.videoComments(videoCommentsDto);
		return Result.OK("查询成功！",comments);
	}
}
