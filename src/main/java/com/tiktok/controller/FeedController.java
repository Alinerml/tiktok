package com.tiktok.controller;

import com.tiktok.bean.Video;
import com.tiktok.bean.dto.FeedDto;
import com.tiktok.bean.dto.VideoDto;
import com.tiktok.bean.vo.FeedVo;
import com.tiktok.common.api.vo.Result;
import com.tiktok.service.IUserService;
import com.tiktok.service.IVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api(tags="feed")
@RestController
@RequestMapping("/feed")
@Slf4j
public class FeedController {
    @Autowired
    private IVideoService videoService;

    @Autowired
    private IUserService userService;

    /**
     *  视频流接口
     */
    @ApiOperation(value="视频流接口", notes="视频流接口")
    @PostMapping()
    public Result feed(@RequestBody FeedDto feedDto) throws IOException, ClassNotFoundException {
        FeedVo feedVo = videoService.feed(feedDto);
        return Result.OK("添加成功！",feedVo);
    }


}
