package com.tiktok.controller;

import java.util.List;
import java.io.IOException;

import com.tiktok.bean.dto.FavoriteListDto;
import com.tiktok.bean.Video;
import com.tiktok.bean.dto.FavoriteActionDto;
import com.tiktok.bean.dto.VideoDto;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IVideoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Api(tags="video")
@RestController
@RequestMapping("")
@Slf4j
public class VideoController {
	@Autowired
	private IVideoService videoService;

	 /**
	  *   视频投稿
	  *
	  * @return
	  */
	 @ApiOperation(value="视频投稿", notes="视频投稿")
	 @PostMapping(value = "/publish/action")
	 public Result<String> action(@RequestBody VideoDto videoDto) throws IOException, ClassNotFoundException {
		 videoService.action(videoDto);
		 return Result.OK("投递成功！");
	 }

	 /**
	  * @description:  添加
	  */
	 @ApiOperation(value="测试-视频添加", notes="测试-视频添加")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/publish/add")
	 public Result<String> add(@RequestBody Video video) {
		 videoService.save(video);
		 return Result.OK("添加成功！");
	 }

	 /**
	  * 发布列表
	  * @param user_id
	  * @return
	  */
	 @ApiOperation(value="发布列表", notes="发布列表")
	 @GetMapping(value = "/publish/list")
	 public Result queryList(@RequestParam(name="user_id",required=true) String user_id) {
		 List<Video> videos = videoService.queryList(user_id);
		 return Result.OK("查询成功！",videos);
	 }

	 /**
	  * 赞操作
	  * @return
	  */
	 @ApiOperation(value="赞操作", notes="赞操作")
	 @PostMapping(value = "/favorite/action")
	 public Result action(@RequestBody FavoriteActionDto favoriteActionDto) {
		 videoService.action(favoriteActionDto);
		 return Result.OK("操作成功！");
	 }

	 /**
	  * 喜欢列表
	  * @return
	  */
	 @ApiOperation(value="喜欢列表", notes="喜欢列表")
	 @PostMapping(value = "/favorite/list")
	 public Result like(@RequestBody FavoriteListDto favoriteListDto) {
		 List<Video> videos = videoService.like(favoriteListDto);
		 return Result.OK("操作成功！",videos);
	 }

}
