package com.oneovern.domain.member.controller;

import com.oneovern.domain.member.dto.MemberReqDto;
import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.exception.code.MemberSuccessCode;
import com.oneovern.domain.member.service.MemberService;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<MemberResDto.Join> signup(@RequestBody @Valid MemberReqDto.Join dto) {

        BaseSuccessCode code = MemberSuccessCode.SIGNED_UP;
        return ApiResponse.onSuccess(code, memberService.join(dto));
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<MemberResDto.Login> login(@RequestBody @Valid MemberReqDto.Login dto) {

        BaseSuccessCode code=MemberSuccessCode.LOGINED;
        return ApiResponse.onSuccess(code, memberService.login(dto));
    }
}
