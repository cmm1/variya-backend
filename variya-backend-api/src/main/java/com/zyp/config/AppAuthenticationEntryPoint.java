package com.zyp.config;

import cn.hutool.json.JSONUtil;
import com.zyp.core.RespBeanEnum;
import com.zyp.core.ResponseResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult responseResult;
        if (authException instanceof UsernameNotFoundException) {
            //用户不存在
            responseResult = ResponseResult.responseError(RespBeanEnum.USER_ACCOUNT_NOT_EXIST.getCode(), RespBeanEnum.USER_ACCOUNT_NOT_EXIST.getMsg(), "");
        } else if (authException instanceof BadCredentialsException) {
            //用户名或密码错误（也就是用户名匹配不上密码）
            responseResult = ResponseResult.responseError(RespBeanEnum.USERNAME_PASSWORD_ERROR.getCode(), RespBeanEnum.USERNAME_PASSWORD_ERROR.getMsg(), "");
        } else if (authException instanceof CredentialsExpiredException) {
            //密码过期
            responseResult = ResponseResult.responseError(RespBeanEnum.USER_PASSWORD_EXPIRED.getCode(), RespBeanEnum.USER_PASSWORD_EXPIRED.getMsg(), "");
        } else if (authException instanceof DisabledException) {
            //账号不可用
            responseResult = ResponseResult.responseError(RespBeanEnum.USER_ACCOUNT_DISABLE.getCode(), RespBeanEnum.USER_ACCOUNT_DISABLE.getMsg(), "");
        } else if (authException instanceof LockedException) {
            //账号锁定
            responseResult = ResponseResult.responseError(RespBeanEnum.USER_ACCOUNT_LOCKED.getCode(), RespBeanEnum.USER_ACCOUNT_LOCKED.getMsg(), "");
        } else {
            //其他错误
            responseResult = ResponseResult.responseError(RespBeanEnum.USER_NOT_LOGIN.getCode(), RespBeanEnum.USER_NOT_LOGIN.getMsg(), "");
        }

        //输出
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter writer=response.getWriter();
        writer.write(JSONUtil.toJsonStr(responseResult));
        writer.flush();
        writer.close();
    }
}
