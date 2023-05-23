package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userdata")
public class UserData {
    @TableId(type =  IdType.AUTO)
    private Integer id;

    private Integer total_favorited;//获赞数量

    private  Integer work_count;//作品数

    private Integer favorite_count;//获赞数量
}
