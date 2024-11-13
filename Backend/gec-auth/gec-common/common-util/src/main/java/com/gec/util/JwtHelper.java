package com.gec.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

//生成json web令牌的工具类
public class JwtHelper {
    //taken过期时间
    private static final long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密密钥
    private static final String tokenSignKey = "123456";

    //根据用户id和名称生成token
    public static String createToken(String userId, String username) {
        String token = Jwts.builder()
                // subject  jwt所面向的用户(只是一个标识而已)
                .setSubject("DAFEI-USER")
                // 设置过期时间     jwt的过期时间，这个过期时间必须要大于签发时间
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpiration))
                // 有效载荷 不要放敏感信息 比如密码
                .claim("userId", userId)
                .claim("username", username)
                // 使用的签名算法 以及 秘钥   Signature 部分是对前两部分的签名，防止数据篡改。
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                // 压缩
                .compressWith(CompressionCodecs.GZIP)
                // 生成token
                .compact();
        return token;
    }
    //从token中获取username
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";
            // 根据秘钥   去解析之前加密的token 字符串 解析出用户名
            String username = (String) Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().get("username");
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //从token字符串获取userid
    public static String getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            // 根据秘钥   去解析之前加密的token 字符串
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

            // 从token中获取body
            Claims claims = claimsJws.getBody();
            //  从body 中获取userId
            String userId = (String) claims.get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
