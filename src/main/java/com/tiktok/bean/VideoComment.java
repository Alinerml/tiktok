package com.tiktok.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: video_comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Data
@TableName("video_comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="video_comment����", description="video_comment")
public class VideoComment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**videoId*/
    @ApiModelProperty(value = "videoId")
    private String videoId;
	/**commentId*/
    @ApiModelProperty(value = "commentId")
    private String commentId;


}
