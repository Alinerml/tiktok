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
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Data
@ApiModel(value="CommentDto", description="CommentDto")
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**video_id*/
    @ApiModelProperty(value = "视频id")
    private String video_id;

    /**action_type*/
    @ApiModelProperty(value = "1-发布评论 2-删除评论")
    private Integer action_type;

    /**comment_text*/
    @ApiModelProperty(value = "用户填写的评论内容，在action_type=1时使用")
    private String comment_text;

    /**comment_id*/
    @ApiModelProperty(value = "要删除的评论id,在action type=2的时候使用")
    private String comment_id;
}
