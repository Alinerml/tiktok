package com.tiktok.service;

import com.tiktok.bean.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiktok.bean.dto.FeedDto;
import com.tiktok.bean.dto.VideoDto;
import com.tiktok.bean.vo.FeedVo;

import java.io.IOException;
import java.util.List;

/**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
public interface IVideoService extends IService<Video> {

    /**
     * 视频投递 -视频新增
     * @param videoDto
     */
    void action(VideoDto videoDto) throws IOException, ClassNotFoundException;

    /**
     * 视频流接口
     * @param feedDto
     */
    FeedVo feed(FeedDto feedDto);

    List<Video> queryList(String user_id);
}
