package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  //禁止CSRF（跨站請求偽造）保護。
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize //對所有訪問HTTP端點的HttpServletRequest進行限制
                        .requestMatchers(
                                "/error/**",   //顯示錯誤訊息
                                "/user",
                                "/create",
                                "/register",
                                "/login",
                                "/product/**",
                                "/upload",
                                "/multiupload",
                                "/img/**"
                        ).permitAll()   //指定上述匹配規則中的路徑，允許所有用戶訪問，即不需要進行身份驗證。
                        .anyRequest().authenticated()   //其他尚未匹配到的路徑都需要身份驗證
                )
                .cors(withDefaults()); // 啟用默認的CORS配置
        return http.build();
    }



}