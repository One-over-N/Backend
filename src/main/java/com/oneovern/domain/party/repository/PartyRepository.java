package com.oneovern.domain.party.repository;

import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.dto.PartyDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    @Query(value = """
            SELECT p.party_id AS partyId, 
                   p.party_name AS partyName, 
                   op.plan_name AS planName, 
                   l.reliability_score AS leaderReliability, 
                   (SELECT COUNT(*) FROM party_member pm WHERE pm.party_id = p.party_id) + 1 AS memberCount,
                   p.party_status AS partyStatus
            FROM party p
            JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id
            JOIN ott o ON op.ott_id = o.ott_id
            JOIN member l ON p.leader_id = l.member_id
            WHERE o.ott_id = :ottId
            """, nativeQuery = true)
    List<PartyDetailProjection> findPartyDetailsByOttId(@Param("ottId") Long ottId);

    @Query(value = """
            SELECT p.party_id AS partyId, 
                   p.party_name AS partyName, 
                   op.plan_name AS planName, 
                   l.reliability_score AS leaderReliability,
                   (SELECT COUNT(*) FROM party_member pm WHERE pm.party_id = p.party_id) + 1 AS memberCount,
                   p.party_status AS partyStatus
            FROM party p
            JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id
            JOIN member l ON p.leader_id = l.member_id
            WHERE p.party_id = :partyId
            """, nativeQuery = true)
    Optional<PartyDetailProjection> findPartyDetailByIdNative(@Param("partyId") Long partyId);
}