package com.oneovern.domain.party.repository;

import com.oneovern.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    @Query(
            value = """
            SELECT p.* FROM party p
            JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id
            JOIN ott o ON op.ott_id = o.ott_id
            WHERE o.ott_id = :ottId
            """,
            nativeQuery = true
    )
    List<Party> findByOttPlan_Ott_OttId(@Param("ottId") Long ottId);
}
