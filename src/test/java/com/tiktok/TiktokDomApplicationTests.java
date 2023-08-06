package com.tiktok;

import com.tiktok.bean.Video;
import com.tiktok.common.utils.JwtUtil;
import com.tiktok.common.utils.UrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
class TiktokDomApplicationTests {

    @Test
    void contextLoads() {
        try {
            String urlString = "https://www.example.com/search?q=java";
            URL url = new URL(urlString);
            System.out.println(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成token
     */
    @Test
    void createToken() {
        String token = JwtUtil.create();
        System.out.println(token);
    }

    @Test
    void urlCreateTest() {
        String play_baseurl = "https://www.douyin.com/play";
        String cover_baseurl = "https://www.douyin.com/cover";
        String play_url = UrlUtil.buildPlayURL(play_baseurl);
        String cover_url = UrlUtil.buildCoverURL(cover_baseurl);
        System.out.println("play_url = "+play_url);
        System.out.println("cover_url = "+cover_url);

    }

    @Test
    void objectToByte() {
//        Object obj =
        Video video = new Video();
        video.setId("2");
        video.setAuthorId("1");
        byte[] bytes;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(video);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();

            System.out.println("bytes ="+bytes);

        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }
}
