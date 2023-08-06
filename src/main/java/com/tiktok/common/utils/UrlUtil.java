package com.tiktok.common.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

//    private static String baseUrl = "https://www.douyin.com/discover"; //偷看官网的


    private static int playCount = 0;
    private static int coverCount = 0;


    /**
     * 生成带递增video参数的play网址
     * @param baseUrl
     * @return
     */
    public static String buildPlayURL(String baseUrl) {
        return baseUrl + "?video=" + (playCount++);
    }

    /**
     * 生成带递增video参数的cover网址
     * @param baseUrl
     * @return
     */
    public static String buildCoverURL(String baseUrl) {
        return baseUrl + "?video=" + (coverCount++);
    }

}
