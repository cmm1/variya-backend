package com.zyp.utils;



import com.zyp.entity.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.val;

import java.security.Key;
import java.util.Date;
import java.util.UUID;


public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

    /**
     * 创建token
     *
     * @param securityUser 用户信息
     * @return token
     */
    public static String createToken(SecurityUser securityUser) {
        val now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(securityUser.getId()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24 * 7))
                .signWith(key)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static void verify(String token, String username) {
        val claims = parseToken(token);
        if (!claims.getSubject().equals(username)) {
            throw new RuntimeException("token非法");
        }
    }

    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            // 获取过期时间
            Long expiration = claims.getExpiration().getTime();
            // 获取当前时间
            Long currentTime = System.currentTimeMillis();
            // 判断是否过期
            return expiration < currentTime;
        } catch (ExpiredJwtException e) {
            System.out.println(1);
            return true;
        } catch (Exception e) {
            // 其他异常，Token可能被篡改等
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(parseToken("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJlZjY1Yzc2Zi0yNTY4LTRhZmUtOTEwMC04OTQ5MjFmYmY1NjEiLCJzdWIiOiIxIiwiaWF0IjoxNzA1MDQ5NDY3LCJleHAiOjE3MDU2NTQyNjd9.VsnRYu3Ynx0lYfK7R2j-ELrJF7Pui6Hbe82S5dZArrfmNFmEAatFomkmJR4Mk4vJeDjedV89ojL6grYxU6H5rQ"));
    }
}
