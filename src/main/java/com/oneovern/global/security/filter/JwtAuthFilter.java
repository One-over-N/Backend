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

                //토큰에서 이메일 추출
                String email = jwtUtil.getEmail(token);

                //인증 객체 생성
                UserDetails user=customUserDetailsService.loadUserByUsername(email);
                Authentication auth=new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                //SecurityContextHolder에 넣음
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handleException(response, GeneralErrorCode.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            log.error("서버 내부 필터 에러: {}", e.getMessage(), e);
            handleException(response, GeneralErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    // 에러 응답 공통 메서드
    private void handleException(HttpServletResponse response, BaseErrorCode code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
