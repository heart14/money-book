package com.liiwe.moneybook.base.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author wfli
 * @since 2025/6/9 16:17
 */
@Slf4j
@Component
public class JwtUtils {

    // 加密密钥
    private static String secretKey = "mb";
    // 访问TOKEN过期时间，单位毫秒
    public static final long ACCESS_EXPIRE = 30 * 60 * 1000; // 30分钟
    // 刷新TOKEN过期时间，单位毫秒
    public static final long REFRESH_EXPIRE = 7 * 24 * 60 * 60 * 1000; // 7天


    /**
     * 初始化签名密钥
     * @return
     */
    private SecretKey generateKey() {
        // 如果密钥不足32位，那么就用0补足长度. 该密钥用于后边的加解密
        int requiredLength = 32 - secretKey.length();
        if (requiredLength > 0) {
            secretKey = secretKey + "0".repeat(requiredLength);
        }
        // 生成KEY
        return Keys.hmacShaKeyFor(secretKey.getBytes());
        /*
        查询hmacShaKeyFor方法的源码可知：
        用于加密的原始(明文)secretKey长度*8之后必须大于等于256，也就是原始长度必须大于等于32
         */
    }

    /**
     * 生成访问TOKEN
     * @param uid
     * @param username
     * @return
     */
    public String generateAccessToken(String uid, String username) {
        return Jwts.builder()
                .subject(uid)
                .issuer(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE))
                .signWith(generateKey())
                .compact();
    }

    /**
     * 生成刷新TOKEN
     * @param uid
     * @param username
     * @return
     */
    public String generateRefreshToken(String uid, String username) {
        return Jwts.builder()
                .subject(uid)
                .issuer(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))
                .signWith(generateKey())
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException|IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 解析TOKEN
     * @param token
     * @return
     */
    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
        /*
        当token过期时，解析失败，报错io.jsonwebtoken.ExpiredJwtException: JWT expired 1491 milliseconds ago at ...
        所以此方法也可用来判断token是否已过期
         */
    }

    /**
     * 解析TOKEN，提取用户名
     * @param token
     * @return
     */
    public String extractUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload().getIssuer();
    }


    /**
     * 测试方法
     * @param args
     */
//    public static void main(String[] args) {
//        String token = generateAccessToken("u01", "wfli");
//        log.info("token: {}", token);
//
//        Claims claims = parseToken(token);
//        log.info("claims: {}", claims);
//
//        String subject = claims.getSubject();
//        log.info("subject: {}", subject);
//
//        JwsHeader header = Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getHeader();
//        log.info("header: {}", header);
//    }

}
/*
 * https://developer.aliyun.com/article/1489264
 */