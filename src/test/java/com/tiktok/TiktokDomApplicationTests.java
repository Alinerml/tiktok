package com.tiktok;

import cn.hutool.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.tiktok.utils.JwtUtil;
import com.tiktok.utils.UrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

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

}
