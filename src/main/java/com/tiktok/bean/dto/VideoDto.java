package com.tiktok.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Data
@ApiModel(value="VideoDto", description="VideoDto")
public class VideoDto implements Serializable {

	/**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**data*/
    @ApiModelProperty(value = "视频数据")
    private byte[] data;

    /**title*/
    @ApiModelProperty(value = "视频标题")
    private String title;

}
