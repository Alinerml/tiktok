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
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;

/**
 * @Description: message
 * @Author: jeecg-boot
 * @Date:   2023-08-10
 * @Version: V1.0
 */
@Data
@TableName("message")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="message����", description="message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @ApiModelProperty(value = "id")
    private String id;
	/**该消息接收者的id*/
    @ApiModelProperty(value = "该消息接收者的id")
    private String toUserId;
	/**该消息发送者的id*/
    @ApiModelProperty(value = "该消息发送者的id")
    private String fromUserId;
	/**消息内容*/
    @ApiModelProperty(value = "消息内容")
    private String content;
	/**消息创建时间*/
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
