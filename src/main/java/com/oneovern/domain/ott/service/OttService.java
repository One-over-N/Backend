package com.oneovern.domain.ott.service;

import com.oneovern.domain.ott.entity.Ott;
import com.oneovern.domain.ott.entity.OttPlan;
import com.oneovern.domain.ott.repository.OttRepository;
import com.oneovern.domain.ott.repository.OttPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OttService {

    private final OttRepository ottRepository;
    private final OttPlanRepository ottPlanRepository;

    public List<Ott> getOttList() {
        return ottRepository.findAll();
    }

    public List<OttPlan> getOttPlans(Long ottId) {
        return ottPlanRepository.findByOttOttServiceId(ottId);
    }
}