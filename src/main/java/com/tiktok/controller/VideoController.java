package com.tiktok.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tiktok.bean.Video;
import com.tiktok.bean.dto.VideoDto;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IVideoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
@RequestMapping("/publish")
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
	 @PostMapping(value = "/action")
	 public Result<String> action(@RequestBody VideoDto videoDto) throws IOException, ClassNotFoundException {
		 videoService.action(videoDto);
		 return Result.OK("投递成功！");
	 }

	 /**
	  * @description:  添加
	  */
	 @ApiOperation(value="测试-视频添加", notes="测试-视频添加")
//	@RequiresPermissions("goods:goods_goods:add")
	 @PostMapping(value = "/add")
	 public Result<String> add(@RequestBody Video video) {
		 videoService.save(video);
		 return Result.OK("添加成功！");
	 }

	 @ApiOperation(value="发布列表", notes="发布列表")
	 @GetMapping(value = "/list")
	 public Result queryList(@RequestParam(name="user_id",required=true) String user_id) {
		 List<Video> videos = videoService.queryList(user_id);
		 return Result.OK("查询成功！",videos);
	 }

}
