package com.oneovern.domain.party.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyReqDto {
    private String partyName;
    private String ottAccountId;
    private String ottAccountPassword;
    private String bank;
    private String bankAccount;
    private Long leaderId;
}