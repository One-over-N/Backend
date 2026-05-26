package com.oneovern.domain.ott.controller;

import com.oneovern.domain.ott.entity.Ott;
import com.oneovern.domain.ott.entity.OttPlan;
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
    public ResponseEntity<List<Ott>> getOtts() {
        List<Ott> ottList = ottService.getOttList();
        return ResponseEntity.ok(ottList);
    }

    @GetMapping("/{ottId}/plans")
    public ResponseEntity<List<OttPlan>> getOttPlans(@PathVariable Long ottId) {
        List<OttPlan> plans = ottService.getOttPlans(ottId);
        return ResponseEntity.ok(plans);
    }
}