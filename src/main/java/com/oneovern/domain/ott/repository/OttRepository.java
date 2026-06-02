package com.oneovern.domain.ott.repository;

import com.oneovern.domain.ott.entity.Ott;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OttRepository extends JpaRepository<Ott, Long> {
}