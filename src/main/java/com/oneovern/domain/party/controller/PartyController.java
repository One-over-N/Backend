package com.oneovern.domain.party.controller;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.party.dto.PartyReqDto;
import com.oneovern.domain.party.dto.PartyResDto;
import com.oneovern.domain.party.enums.RequestStatus;
import com.oneovern.domain.party.service.PartyService;
import com.oneovern.global.apiPayload.ApiResponse;
import com.oneovern.global.apiPayload.code.GeneralSuccessCode;
import com.oneovern.global.security.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ott-service")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/{serviceId}/{planId}")
    public ApiResponse<Long> createParty(
            @PathVariable(name = "serviceId") Long serviceId,
            @PathVariable(name = "planId") Long planId,
            @AuthUser Member member,
            @RequestBody PartyReqDto partyReqDto
    ) {
        Long partyId = partyService.createParty(planId, member, partyReqDto);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, partyId);
    }

    @GetMapping("/{ottId}/parties")
    public ApiResponse<List<PartyResDto.PartyInquiryDto>> getPartiesByOtt(
            @PathVariable(name = "ottId") Long ottId
    ) {
        List<PartyResDto.PartyInquiryDto> resList = partyService.getPartiesByOtt(ottId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, resList);
    }

    @GetMapping("/parties/{partyId}")
    public ApiResponse<PartyResDto.PartyDetailDto> getPartyDetail(
            @PathVariable(name = "partyId") Long partyId
    ) {
        PartyResDto.PartyDetailDto res = partyService.getPartyDetail(partyId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, res);
    }

    @PostMapping("/parties/{partyId}/join")
    public ApiResponse<Long> requestJoin(
            @PathVariable(name = "partyId") Long partyId,
            @AuthUser Member member
    ) {
        Long requestId = partyService.requestJoin(partyId, member);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, requestId);
    }

    @PatchMapping("/parties/join-requests/{requestId}")
    public ApiResponse<String> processJoinRequest(
            @PathVariable(name = "requestId") Long requestId,
            @RequestParam(name = "status") RequestStatus status,
            @AuthUser Member leader
    ) {
        partyService.processJoinRequest(requestId, status, leader);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "가입 요청 처리가 완료되었습니다.");
    }

    // 내가 만든 파티 목록
    @GetMapping("/parties/my")
    public ApiResponse<List<PartyResDto.PartyInquiryDto>> getMyParties(@AuthUser Member member) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, partyService.getMyParties(member));
    }

    // 내가 참여 중인 파티 목록
    @GetMapping("/parties/joined")
    public ApiResponse<List<PartyResDto.PartyInquiryDto>> getJoinedParties(@AuthUser Member member) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, partyService.getJoinedParties(member));
    }
}