package com.tiktok.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * @Description: comment
 * @Author: jeecg-boot
 * @Date:   2023-08-07
 * @Version: V1.0
 */
@Data
@TableName("comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="comment����", description="comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @ApiModelProperty(value = "id")
    private String id;
	/**评论用户id*/
    @ApiModelProperty(value = "评论用户id")
    private String userId;
	/**评论内容*/
    @ApiModelProperty(value = "评论内容")
    private String content;
	/**评论发布日期*/
    @ApiModelProperty(value = "评论发布日期")
    private Integer createTime;
}
