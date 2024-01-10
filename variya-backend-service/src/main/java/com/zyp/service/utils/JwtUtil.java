package com.zyp.service.utils;



import com.zyp.pojo.entity.SecurityUser;
import io.jsonwebtoken.Claims;
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
}
