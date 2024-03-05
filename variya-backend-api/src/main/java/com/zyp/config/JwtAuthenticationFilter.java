package com.zyp.config;


import com.zyp.consts.CacheKeyConst;

import com.zyp.entity.SecurityUser;

import com.zyp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedissonClient redissonClient;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = httpServletRequest.getHeader("authorization");
        if(!StringUtils.hasText(token)){
            // 如果没有token则进行放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        // 解析token
        String userId ;
        try {
            Claims claims = JwtUtil.parseToken(token);
            userId = claims.getSubject().toString();
        } catch (Exception e) {
            throw new RuntimeException("token非法");
        }
        // 从redis中获取token
        RBucket<String> bucket = redissonClient.getBucket(CacheKeyConst.USER_TOKEN + userId);
        String cacheToken = bucket.get();
        if(StringUtils.isEmpty(cacheToken)){
            throw new CredentialsExpiredException("用户未登录");
        }
        //是否过期
//        boolean tokenExpired = JwtUtil.isTokenExpired(token);
//        if(tokenExpired){
//            throw new CredentialsExpiredException("用户未登录");
//        }
        //获取用户信息

        // 存入SecurityContextHolder
        //  获取权限信息，封装到Authentication
        RBucket<Object> bucketUser = redissonClient.getBucket(CacheKeyConst.USER_INFO + userId);
        SecurityUser securityUser = (SecurityUser) bucketUser.get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities()));
        // 放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
