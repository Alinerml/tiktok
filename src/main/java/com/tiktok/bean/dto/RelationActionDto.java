package com.tiktok.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="RelationActionDto", description="RelationActionDto")
public class RelationActionDto {
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**user_id*/
    @ApiModelProperty(value = "对方用户id")
    private String to_user_id;

    /**action_type*/
    @ApiModelProperty(value = "1-关注 2-取消关注")
    private String action_type;

}
