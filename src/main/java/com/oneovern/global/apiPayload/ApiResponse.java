package com.oneovern.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.oneovern.global.apiPayload.code.BaseErrorCode;
import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"}) //json 형식 지정
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    @JsonProperty("code")
    private final String code;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("result")
    private T result;

    //성공
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result){
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    //실패
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result){
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}
