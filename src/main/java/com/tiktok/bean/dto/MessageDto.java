package com.tiktok.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: message
 * @Author: jeecg-boot
 * @Date:   2023-08-10
 * @Version: V1.0
 */
@Data
@ApiModel(value="MessageDto", description="MessageDto")
public class MessageDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;
	/**该消息接收者的id*/
    @ApiModelProperty(value = "该消息接收者的id")
    private String to_user_id;
    /**1-发送消息*/
    @ApiModelProperty(value = "1-发送消息")
    private Integer action_type;
	/**消息内容*/
    @ApiModelProperty(value = "消息内容")
    private String content;
    /**消息创建时间*/
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
