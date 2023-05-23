package com.tiktok.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("playinfo")
public class PlayInfo {
    @TableId(type =  IdType.AUTO)
    private Integer id;

    private String play_url;//视频播放地址

    private String cover_url;//视频封面地址

    private Integer favorite_count;//视频的点赞总数

    private Integer comment_count;//视频的评论总数

    private boolean is_favorite;//true-已点赞，false-未点赞

    private String title;//视频标题

    private Path data; //视频数据
}
