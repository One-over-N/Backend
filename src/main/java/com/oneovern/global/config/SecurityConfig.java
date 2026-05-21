package com.oneovern.global.config;

import com.oneovern.global.security.exception.CustomAccessDenied;
import com.oneovern.global.security.exception.CustomEntryPoint;
import com.oneovern.global.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomEntryPoint customEntryPoint;
    private final CustomAccessDenied customAccessDenied;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests->requests
                        .anyRequest().authenticated() //모든 요청 인증 요구
                )
                .formLogin(AbstractHttpConfigurer::disable) //폼 로그인 비활성화
                .sessionManagement(session->session //세션 관리 비활성화
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //(id/pw 검사 전에) jwt 필터 추가
                .logout(AbstractHttpConfigurer::disable)
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web->web.ignoring().requestMatchers(
                // Swagger 허용
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                //로그인
                "/auth/login",
                //회원가입
                "/auth/signup"
        );
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthtFilterRegistrationBean(JwtAuthFilter filter){
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false); //JwtAuthFilter 톰캣 전역 필터로 미등록
        return registration;
    }

}
