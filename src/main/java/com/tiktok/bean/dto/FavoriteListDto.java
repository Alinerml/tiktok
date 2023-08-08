package com.tiktok.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="FavoriteListDto", description="FavoriteListDto")
public class FavoriteListDto {
    /**token*/
    @ApiModelProperty(value = "token")
    private String token;

    /**user_id*/
    @ApiModelProperty(value = "用户id")
    private String user_id;
}
