package com.tiktok.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Data
@ApiModel(value="FavoriteActionDto", description="FavoriteActionDto")
public class FavoriteActionDto implements Serializable {

	/**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**video_id*/
    @ApiModelProperty(value = "视频id")
    private String video_id;

    /**action_type*/
    @ApiModelProperty(value = "1-点赞，2-取消点赞")
    private String action_type;

}
