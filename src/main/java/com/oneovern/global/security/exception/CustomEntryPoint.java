package com.oneovern.global.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneovern.global.ApiResponse;
import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.code.GeneralErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BaseErrorCode code= GeneralErrorCode.UNAUTHORIZED;

        //응답 Content-Type, HTTP 상태코드 정의
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        //Response Body에 응답 통일한 객체 넣기
        ApiResponse<Void> errorResponse=ApiResponse.onFailure(code, null);

        //실제 Response로 덮어쓰기
        mapper.writeValue(response.getWriter(), errorResponse);
    }
}
