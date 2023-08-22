package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: friend_user
 * @Author: jeecg-boot
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Data
@TableName("friend_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FriendUser", description="FriendUser")
public class FriendUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**和该好友的最新聊天消息*/
    @ApiModelProperty(value = "和该好友的最新聊天消息")
    private String message;
    /**0=>当前清求用户接收的消息，1=>当前清求用户发送的消息*/
    @ApiModelProperty(value = "0=>当前清求用户接收的消息，1=>当前清求用户发送的消息")
    private Integer msgType;
    /**用户名称*/
    @ApiModelProperty(value = "用户名称")
    private String name;
    /**关注总数*/
    @ApiModelProperty(value = "关注总数")
    private Integer followCount;
    /**粉丝总数*/
    @ApiModelProperty(value = "粉丝总数")
    private Integer followerCount;
    /**1-已关注 0-未关注*/
    @ApiModelProperty(value = "1-已关注 0-未关注")
    private Boolean isFollow;
    /**用户头像*/
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    /**用户个人页顶部大图*/
    @ApiModelProperty(value = "用户个人页顶部大图")
    private String backgroundImage;
    /**个人简介*/
    @ApiModelProperty(value = "个人简介")
    private String signature;
    /**获赞数量*/
    @ApiModelProperty(value = "获赞数量")
    private Integer totalFavorited;
    /**作品数量*/
    @ApiModelProperty(value = "作品数量")
    private Integer workCount;
    /**点赞数量*/
    @ApiModelProperty(value = "点赞数量")
    private Integer favoriteCount;
}