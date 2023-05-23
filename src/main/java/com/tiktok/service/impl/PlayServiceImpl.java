package com.tiktok.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiktok.bean.*;
import com.tiktok.config.contributeException;
import com.tiktok.mapper.PlayMapper;
import com.tiktok.mapper.UserMapper;
import com.tiktok.service.IPlayService;
import com.tiktok.service.IUserService;
import com.tiktok.utils.JwtUtil;
import com.tiktok.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.HashMap;

@Service
public class PlayServiceImpl extends ServiceImpl<PlayMapper, PlayInfo>  implements IPlayService {
    @Autowired
    PlayMapper playMapper;

    @Autowired
    UserMapper userMapper;

    static String play_baseurl = "https://www.douyin.com/play";

    static String cover_baseurl = "https://www.douyin.com/cover";

    @Override
    public void contribute(String data, String token, String title) {
        //先校验令牌token -需要什么token对应值去改JwtUtil中常量 然后去测试类中查看
        try {
            JwtUtil.verifyToken(token);
        } catch (Exception e) {
            throw new contributeException("token校验失败");
        }

        //视频数据存储数据库 （data和title 其他自设一些默认值）
        //自动生成 play_url  String play_url = UrlUtil.buildPlayURL(play_baseurl);
        //        String cover_url = UrlUtil.buildCoverURL(cover_baseurl); 和 cover_url


        try {
            playMapper.InsertContribute(data, title, play_baseurl, cover_baseurl);
        } catch (Exception e) {
            throw new contributeException("data和title上传失败："+e.getMessage());
        }
    }

    @Override
    public HashMap<String, Object> distribute(String token, String user_id) {
        try {
            JwtUtil.verifyToken(token);
        } catch (Exception e) {
            throw new contributeException("token校验失败");
        }
        //把String类型id转Integer
        int id = Integer.parseInt(user_id);

        // 先返回所有user数据 包装为author
        UserInfo userInfo = userMapper.selectByIdFromUserInfo(id);
        UserData userData = userMapper.selectByIdFromUserData(id);
        UserFile userFile = userMapper.selectByIdFromUserFile(id);
        UserFollow userFollow = userMapper.selectByIdFromUserFollow(id);
        HashMap<String, Object> author = new HashMap<String, Object>();
        author.put("id",userInfo.getId());author.put("name",userInfo.getUsername());
        author.put("follow_count",userFollow.getFollow_count());author.put("follower_count",userFollow.getFollower_count());author.put("is_follow",userFollow.is_follow());
        author.put("avatar",userFile.getAvatar());author.put("background_image",userFile.getBackground_image());author.put("signature",userFile.getSignature());
        author.put("total_favorited",userData.getTotal_favorited());author.put("work_count",userData.getWork_count());author.put("favorite_count",userData.getFavorite_count());

        //在把 play 和 author 包装为 video_list
        HashMap<String, Object> video_list = new HashMap<>();
        //将user_id和play中id对应起来 -先默认查询play id=1情况
        PlayInfo playInfo = playMapper.SelectById(1);
        video_list.put("id",playInfo.getId());
        video_list.put("author",author);
        video_list.put("play_url",playInfo.getPlay_url());
        video_list.put("cover_url",playInfo.getCover_url());
        video_list.put("favorite_count",playInfo.getFavorite_count());
        video_list.put("comment_count",playInfo.getComment_count());
        video_list.put("is_favorite",playInfo.is_favorite());
        video_list.put("title",playInfo.getTitle());

        return video_list;
    }
}
