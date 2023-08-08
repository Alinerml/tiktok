package com.tiktok.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;

/**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Data
@TableName("video")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="video����", description="video")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;
	/**视频作者id*/
    @ApiModelProperty(value = "视频作者id")
    private String authorId;
	/**视频播放地址*/
    @ApiModelProperty(value = "视频播放地址")
    private String playUrl;
	/**视频封面地址*/
    @ApiModelProperty(value = "视频封面地址")
    private String coverUrl;
	/**视频点赞总数*/
    @ApiModelProperty(value = "视频点赞总数")
    private Integer favoriteCount;
	/**视频评论总数*/
    @ApiModelProperty(value = "视频评论总数")
    private Integer commentCount;
	/**1-已点赞 0-未点赞*/
    @ApiModelProperty(value = "1-已点赞 0-未点赞")
    private Boolean isFavorite;
	/**视频标题*/
    @ApiModelProperty(value = "视频标题")
    private String title;
    /**创建时间*/ //Integer方便做比较
    @ApiModelProperty(value = "创建时间")
    private BigInteger createTime;

}
