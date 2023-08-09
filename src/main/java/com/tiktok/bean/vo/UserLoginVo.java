package com.tiktok.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="UserLoginVo", description="UserLoginVo")
public class UserLoginVo {
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**user_id*/
    @ApiModelProperty(value = "用户id")
    private String user_id;
}
