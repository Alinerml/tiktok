package com.tiktok.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录注册用户基本信息表
 */
@Data
@ApiModel(value="UserInfoDto", description="登录注册用户基本信息表Dto")
public class UserInfoDto {

    /**用户name*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "用户name")
    private String username;

    /**密码*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "密码")
    private String password;
}
