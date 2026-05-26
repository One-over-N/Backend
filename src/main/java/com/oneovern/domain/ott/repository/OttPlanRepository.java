package com.oneovern.domain.ott.repository;

import com.oneovern.domain.ott.entity.OttPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OttPlanRepository extends JpaRepository<OttPlan, Long> {
    List<OttPlan> findByOttOttServiceId(Long ottId);
}