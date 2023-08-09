package com.tiktok.common.utils;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sun.org.apache.regexp.internal.RE;
import com.tiktok.bean.UserInfo;
import com.tiktok.bean.dto.UserInfoDto;
import com.tiktok.mapper.UserInfoMapper;
import com.tiktok.service.IUserInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    @Autowired
    private static IUserInfoService userInfoService;


    private static final String SECRET_KEY = "U2VjcmV0S2V5VGVzdDEyMzQ1Njc4OUFCQ0RFRkdISUpLTE1OT1BRUlNUVVZXWFla";
    private static final long EXPIRATION_TIME = 86400000; // 24小时内有效

    // 生成一个随机的secretKey
    public static String getSecretKey() { //随机生成会导致鉴权验权失败
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // 将secretKey转换为Base64编码的字符串
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return encodedKey;
    }

    // 生成令牌
    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 验证令牌
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

//        //去和userInfo中token检验
//        String userIdFromToken = getUserIdFromToken(token);
//        UserInfo userInfo = userInfoService.getById(userIdFromToken); //注入userInfoService失败
//        String userInfoToken = userInfo.getToken();
//
//        if (token.equals(userInfoToken)) {
//            return true;
//        } else {
//            return false;
//        }
    }

    // 从令牌中获取用户ID
    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}