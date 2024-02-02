package com.zyp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity //开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;


    @Bean
    @Override
    public AuthenticationManager  authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 密码加密
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                                                               // 关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // 指定session的创建策略，不使用session
                .and()                                                                          // 再次获取到HttpSecurity对象
                .authorizeRequests()                                                            // 进行认证请求的配置
                .antMatchers("/auth/login").anonymous()                              // 对于登录接口，允许匿名访问
                .antMatchers("/images/*").anonymous()
                .antMatchers("/excel/*").anonymous()
                .antMatchers("/ws/sendMessage").anonymous()
                .antMatchers("/doc.html","/swagger-ui.html", "/webjars/**", "/v2/api-docs", "/swagger-resources/**", "/configuration/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                //认证失败处理器
                .authenticationEntryPoint(appAuthenticationEntryPoint)
                .and()
                //允许跨域
                .cors().configurationSource(configurationSource())
                .and()
                //
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .accessDecisionManager(customAccessDecisionManager); // 设置自定义的AccessDecisionManager
    }

    //跨域配置
    CorsConfigurationSource configurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    /**
     * 重写configure web方法，忽略auth和error路径
     * @param web
     */
    @Override
    public void configure(WebSecurity web){
        //忽略的路径
        web.ignoring().antMatchers("/error/**");
    }
}
