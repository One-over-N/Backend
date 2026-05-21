package com.oneovern.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.code.GeneralErrorCode;
import com.oneovern.global.security.service.CustomUserDetailsService;
import com.oneovern.global.security.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try{
            String token = request.getHeader("Authorization");

            //토큰이 없거나 Bearer가 아니면 넘기기
            if(token==null||!token.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            //Bearer이면 추출
            token=token.replace("Bearer ", "");

            //AccessToken 검증
            if(jwtUtil.isValid(token)){

                //Redis 블랙리스트에 등록된 토큰인지 확인
                if (Boolean.TRUE.equals(redisTemplate.hasKey("BL:"+token))){
                    throw new JwtException("로그아웃된 토큰입니다.");
                }

                //토큰에서 이메일 추출
                String email = jwtUtil.getEmail(token);

                //인증 객체 생성
                UserDetails user=customUserDetailsService.loadUserByUsername(email);
                Authentication auth=new UsernamePasswordAuthenticationToken(
                        user,
                        token,
                        user.getAuthorities()
                );

                //SecurityContextHolder에 넣음
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.warn("JWT 인증 실패: {}", e.getMessage());
            handleException(response, GeneralErrorCode.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("서버 내부 필터 에러: {}", e.getMessage(), e);
            handleException(response, GeneralErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 에러 응답 공통 메서드
    private void handleException(HttpServletResponse response, BaseErrorCode code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
