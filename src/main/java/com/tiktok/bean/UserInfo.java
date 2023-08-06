package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * 登录注册用户基本信息表
 */
@Data
@TableName("userinfo")
@ApiModel(value="UserInfo", description="登录注册用户基本信息表")
public class UserInfo {

    /**用户id*/
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @ApiModelProperty(value = "id,和user表中同id")
    private String id;

    /**用户name*/
    @ApiModelProperty(value = "用户name")
    private String username;

    /**密码*/
    @ApiModelProperty(value = "密码")
    private String password;
}
