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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.oneovern.domain.member.dto.MemberReqDto;

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

    //마이페이지 프로필 정보(닉네임, 이메일) 수정 API
    @PatchMapping("/mypage/profile")
    public ApiResponse<String> updateProfile(
            @AuthUser Member member,
            @RequestBody MemberReqDto.UpdateProfileDto request
    ) {
        memberService.updateProfile(member, request);
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_FOUND, "프로필 정보가 성공적으로 수정되었습니다.");
    }

}
