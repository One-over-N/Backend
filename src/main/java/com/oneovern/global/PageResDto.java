package com.oneovern.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResDto<T> {
    @Builder.Default
    private List<T> dataList = new ArrayList<>();
    private boolean isLast;
    private Long nextCursor;
}
