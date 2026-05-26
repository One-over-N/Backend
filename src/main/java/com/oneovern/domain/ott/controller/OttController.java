package com.oneovern.domain.ott.controller;

import com.oneovern.domain.ott.dto.OttResDto;
import com.oneovern.domain.ott.dto.OttPlanResDto;
import com.oneovern.domain.ott.service.OttService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otts")
public class OttController {

    private final OttService ottService;

    @GetMapping
    public ResponseEntity<List<OttResDto>> getOtts() {
        List<OttResDto> ottList = ottService.getOttList().stream()
                .map(OttResDto::from)
                .toList();
        return ResponseEntity.ok(ottList);
    }

    @GetMapping("/{ottId}/plans")
    public ResponseEntity<List<OttPlanResDto>> getOttPlans(@PathVariable Long ottId) {
        List<OttPlanResDto> plans = ottService.getOttPlans(ottId).stream()
                .map(OttPlanResDto::from)
                .toList();
        return ResponseEntity.ok(plans);
    }
}