package com.tiktok.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="MessageDto", description="MessageDto")
public class MessageChatDto {
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;
    /**该消息接收者的id*/
    @ApiModelProperty(value = "该消息接收者的id")
    private String to_user_id;

    /**上次最新消息的时间（新增字段-apk更新中）*/
    @ApiModelProperty(value = "上次最新消息的时间（新增字段-apk更新中）")
    private String pre_msg_time;
}
