package com.tiktok.bean.vo;

import com.tiktok.bean.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
@Data
@ApiModel(value="FeedVo", description="FeedVo")
public class FeedVo {
    /**filteredVideos*/
    @ApiModelProperty(value = "过滤后视频列表")
    List<Video> video_list;

    /**本次返回的视频中，发布最早的时间*/ //但不会自动生成
    @ApiModelProperty(value = "本次返回的视频中，发布最早的时间")
    private BigInteger next_time;
}
