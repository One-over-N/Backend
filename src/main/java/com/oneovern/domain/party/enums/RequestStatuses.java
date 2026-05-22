package com.oneovern.domain.party.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatuses
{
    PENDING("보류"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String description;
}
