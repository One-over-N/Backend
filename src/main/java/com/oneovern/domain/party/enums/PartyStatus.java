package com.oneovern.domain.party.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartyStatus {

    RECRUITING("모집 중"),
    CLOSED("모집 완료"),
    ACTIVE("활성화");

    private final String description;

}
