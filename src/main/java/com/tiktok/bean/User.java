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
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;

/**
 * @Description: user
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Data
@TableName("user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user", description="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

	/**用户id*/
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @ApiModelProperty(value = "用户id")
    private String id;
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
