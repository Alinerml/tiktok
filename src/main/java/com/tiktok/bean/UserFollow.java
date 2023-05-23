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
@TableName("userfollow")
public class UserFollow {
    @TableId(type =  IdType.AUTO)
    private Integer id;

    private Integer follow_count; //关注总数

    private Integer follower_count; //粉丝总数

    private boolean is_follow;//true-已关注，false-未关注


}
