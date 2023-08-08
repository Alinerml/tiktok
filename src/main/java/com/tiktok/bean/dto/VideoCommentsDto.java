package com.tiktok.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="VideoCommentsDto", description="VideoCommentsDto")
public class VideoCommentsDto {
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**video_id*/
    @ApiModelProperty(value = "视频id")
    private String video_id;
}
