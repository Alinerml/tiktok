package com.tiktok.common.utils;

import cn.hutool.core.date.chinese.SolarTerms;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String PASSWORD = "aaaa";

    public static String create() {
        Map<String, Object> map = new HashMap<String, Object>();
        return JWTUtil.createToken(map, PASSWORD.getBytes());
    }

    public static boolean verifyToken(String token) {
        return JWTUtil.verify(token, PASSWORD.getBytes());
    }
}
