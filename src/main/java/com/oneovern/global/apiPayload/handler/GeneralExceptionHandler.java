package com.oneovern.global.apiPayload.handler;

import com.oneovern.global.ApiResponse;
import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.code.GeneralErrorCode;
import com.oneovern.global.apiPayload.exception.ProjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //컨트롤러 에러 확인
public class GeneralExceptionHandler {

    //프로젝트 에러
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e){
        BaseErrorCode errorCode=e.getBaseErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode,null));
    }

    //@Valid 에러(MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        //첫번째 에러 메시지
        String errorMessage=ex.getBindingResult().getFieldError().getDefaultMessage();
        BaseErrorCode code=GeneralErrorCode.BAD_REQUEST;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, errorMessage));

    }

    //그 외 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex){
        BaseErrorCode code= GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(code, ex.getMessage()));
    }


}
