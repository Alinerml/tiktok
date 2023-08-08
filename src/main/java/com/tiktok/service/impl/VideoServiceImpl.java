package com.tiktok.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tiktok.bean.Video;
import com.tiktok.bean.dto.FavoriteListDto;
import com.tiktok.bean.dto.FeedDto;
import com.tiktok.bean.dto.FavoriteActionDto;
import com.tiktok.bean.dto.VideoDto;
import com.tiktok.bean.vo.FeedVo;
import com.tiktok.mapper.VideoMapper;
import com.tiktok.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

/**
 * @Description: video
 * @Author: jeecg-boot
 * @Date:   2023-08-03
 * @Version: V1.0
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
    @Autowired
    VideoMapper videoMapper;

    @Override
    public void action(VideoDto videoDto) throws IOException, ClassNotFoundException {
        //鉴权token

        //读取data转为Video -对应自测文件objectToByte-解码失败，问问前端会给怎么样的data
        try {
            byte[] data = videoDto.getData();
            ByteArrayInputStream bi = new ByteArrayInputStream(data);
            ObjectInputStream oi = new ObjectInputStream(bi);
            Video video = (Video)oi.readObject();
            bi.close();
            oi.close();

            this.save(video);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public FeedVo feed(FeedDto feedDto) {
        //以latest_time 去查询 video_list 和 next_time -单次最多30个video
        BigInteger latest_time = feedDto.getLatest_time();

        //1.先能查出latest_time 对应所有的video_list，并且返回最早的next_time
        MPJLambdaWrapper<Video> wrapper = new MPJLambdaWrapper<Video>()
                .selectAll(Video.class)
                //gt表示查找所有大于latest_time的对象，因为2024>2023，等同于查找所有发布时间早的
                .gt(Video::getCreateTime,latest_time);
        List<Video> videos = videoMapper.selectJoinList(Video.class,wrapper);

        //2.再限制30个
        List<Video> filteredVideos;
        if (videos.size() > 30) {
            filteredVideos = videos.subList(0, 30);
        } else {
            filteredVideos = videos;
        }

        Video nextVideo = filteredVideos.stream()
                .max(Comparator.comparing(Video::getCreateTime)) //使用 max() 方法和一个比较器来找到 CreateTime 最大的视频对象
                .orElse(null);
        BigInteger next_time = nextVideo.getCreateTime();

        FeedVo feedVo = new FeedVo();
        feedVo.setVideo_list(filteredVideos);
        feedVo.setNext_time(next_time);

        return feedVo;
    }

    @Override
    public List<Video> queryList(String user_id) {
        //列出用户所有投稿过的视频
        //1.去video中查该id下全部对象
        MPJLambdaWrapper<Video> wrapper = new MPJLambdaWrapper<Video>()
                .selectAll(Video.class)
                .eq(Video::getAuthorId,user_id);
        List<Video> videos = videoMapper.selectJoinList(Video.class, wrapper);

        return videos;
    }

    @Override
    public void action(FavoriteActionDto favoriteActionDto) {
        //改个video_id 的is_favorite
        String token = favoriteActionDto.getToken();
        //1.token操作


        String videoId = favoriteActionDto.getVideo_id();
        String actionType = favoriteActionDto.getAction_type();

        Video video = this.getById(videoId);
        //2.直接取反不就好了
        video.setIsFavorite(!video.getIsFavorite());
        //修改favorite_count数量
        if (actionType.equals("1")) video.setFavoriteCount(video.getFavoriteCount()+1);
        if (actionType.equals("2")) video.setFavoriteCount(video.getFavoriteCount()-1);

        this.updateById(video);
    }

    @Override
    public List<Video> like(FavoriteListDto favoriteListDto) {
        String token = favoriteListDto.getToken();
        //token鉴权

        String userId = favoriteListDto.getUser_id();
        MPJLambdaWrapper<Video> wrapper = new MPJLambdaWrapper<Video>()
                .selectAll(Video.class)
                .eq(Video::getAuthorId,userId)
                .eq(Video::getIsFavorite,true);
        List<Video> videos = videoMapper.selectJoinList(Video.class, wrapper);

        return videos;
    }
}
