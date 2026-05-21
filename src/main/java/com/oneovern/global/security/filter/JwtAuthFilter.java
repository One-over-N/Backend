package com.oneovern.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.code.GeneralErrorCode;
import com.oneovern.global.security.service.CustomUserDetailsService;
import com.oneovern.global.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customuserDetailsService;

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
                UserDetails user=customuserDetailsService.loadUserByUsername(email);
                Authentication auth=new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                //SecurityContextHolder에 넣음
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            BaseErrorCode code= GeneralErrorCode.UNAUTHORIZED;

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(code.getStatus().value());

            ApiResponse<Void> errorResponse=ApiResponse.onFailure(code, null);

            mapper.writeValue(response.getOutputStream(), errorResponse);
        }
    }
}
