package com.oneovern.domain.party.dto;

import lombok.Getter;

public class PartyReqDto {

    @Getter
    public static class ProcessApplicationRequest {

        private String status;

    }
}