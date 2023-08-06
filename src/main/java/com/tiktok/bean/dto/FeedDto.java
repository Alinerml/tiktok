package com.tiktok.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.util.Date;

@Data
@ApiModel(value="FeedDto", description="FeedDto")
public class FeedDto {
    /**最新投稿时间戳*/ //但不会自动生成
    @ApiModelProperty(value = "最新投稿时间戳")
    private BigInteger latest_time;

    /**token*/
    @ApiModelProperty(value = "token")
    private String token;
}
