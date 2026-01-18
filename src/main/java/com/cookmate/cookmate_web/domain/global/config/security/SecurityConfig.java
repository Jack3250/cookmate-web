package com.cookmate.cookmate_web.domain.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @file        SecurityConfig.java
 * @description 시큐리티 설정
 * @author      강보람
 * @since       2026-01-18
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-18      강보람       최초 생성
 * </pre>
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘 사용
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API 방식이므로 CSRF 비활성화
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // H2 콘솔 사용 시 필요
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/users/regist"
                        , "/users/login"
                        , "/common/**"
                    ).permitAll() // 가입 및 공통 메시지 허용
                .anyRequest().authenticated() // 그 외는 인증 필요
            );

        return http.build();
    }
}
