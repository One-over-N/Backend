package com.oneovern.global.config;

import com.oneovern.global.security.exception.CustomAccessDenied;
import com.oneovern.global.security.exception.CustomEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomEntryPoint customEntryPoint;
    private final CustomAccessDenied customAccessDenied;

    private final String[] allowUrls={ //인증 없이 접근 가능 경로들
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            //로그인
            "/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests->requests
                        .requestMatchers(allowUrls).permitAll() //인증 없이 접근 가능 경로 지정
                        .anyRequest().authenticated() //그 외 모든 요청 인증 요구
                )
                .formLogin(form->form
                        .defaultSuccessUrl("/swagger-ui/index.html", true) //로그인 성공시 이동
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/logout") //로그아웃 경로
                        .logoutSuccessUrl("/login?logout") //로그아웃 성공시 이동
                        .permitAll()
                )
                //예외 상황 핸들러
                .exceptionHandling(exception->exception
                        .accessDeniedHandler(customAccessDenied)
                        .authenticationEntryPoint(customEntryPoint)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //비밀번호 솔트
        return new BCryptPasswordEncoder();
    }

}
