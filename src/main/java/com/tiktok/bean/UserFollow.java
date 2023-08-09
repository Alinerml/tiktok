package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

@Data
@TableName("user_follow")
@ApiModel(value="UserFollow", description="UserFollow")
public class UserFollow {
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @ApiModelProperty(value = "id")
    private String id;

    /**userId*/
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**followUserId*/
    @ApiModelProperty(value = "关注用户id")
    private String followUserId;
}
