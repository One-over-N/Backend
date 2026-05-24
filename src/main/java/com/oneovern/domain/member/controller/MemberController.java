package com.oneovern.domain.member.controller;

import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.exception.code.MemberSuccessCode;
import com.oneovern.domain.member.service.MemberService;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.security.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    //마이페이지 프로필 및 신뢰도 점수 조회
    @GetMapping("/mypage")
    public ApiResponse<MemberResDto.MyPage> getMyPage(@AuthUser Member member) {
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_FOUND, memberService.getMyPageInfo(member));
    }
}
