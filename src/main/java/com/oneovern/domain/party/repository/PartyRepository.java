package com.oneovern.domain.party.repository;

import com.oneovern.domain.party.entity.Party;
import com.oneovern.domain.party.dto.PartyDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO join_request (request_status, member_id, party_id, created_at, updated_at) VALUES (:status, :memberId, :partyId, NOW(), NOW())", nativeQuery = true)
    void saveJoinRequestNative(@Param("status") String status, @Param("memberId") Long memberId, @Param("partyId") Long partyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE join_request SET request_status = :status WHERE join_request_id = :requestId", nativeQuery = true)
    void updateJoinRequestStatusNative(@Param("requestId") Long requestId, @Param("status") String status);

    @Query(value = "SELECT member_id FROM join_request WHERE join_request_id = :requestId", nativeQuery = true)
    Long findMemberIdByRequestIdNative(@Param("requestId") Long requestId);

    @Query(value = "SELECT p.leader_id FROM join_request jr JOIN party p ON jr.party_id = p.party_id WHERE jr.join_request_id = :requestId", nativeQuery = true)
    Long findLeaderIdByRequestIdNative(@Param("requestId") Long requestId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO party_member (party_id, member_id, created_at, updated_at) VALUES (:partyId, :memberId, NOW(), NOW())", nativeQuery = true)
    void savePartyMemberNative(@Param("partyId") Long partyId, @Param("memberId") Long memberId);

    @Query(value = "SELECT party_id FROM join_request WHERE join_request_id = :requestId", nativeQuery = true)
    Long findPartyIdByRequestIdNative(@Param("requestId") Long requestId);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM join_request WHERE party_id = :partyId AND member_id = :memberId AND request_status IN ('PENDING', 'APPROVED'))", nativeQuery = true)
    int existsActiveJoinRequest(@Param("partyId") Long partyId, @Param("memberId") Long memberId);

    @Query(value = "SELECT COUNT(*) + 1 FROM party_member WHERE party_id = :partyId", nativeQuery = true)
    int getCurrentMemberCountNative(@Param("partyId") Long partyId);

    @Query(value = """
    SELECT p.party_id AS partyId, p.party_name AS partyName,
           op.plan_name AS planName, l.reliability_score AS leaderReliability,
           (SELECT COUNT(*) FROM party_member pm WHERE pm.party_id = p.party_id) + 1 AS memberCount,
           p.party_status AS partyStatus
    FROM party p
    JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id
    JOIN member l ON p.leader_id = l.member_id
    WHERE p.leader_id = :leaderId
    """, nativeQuery = true)
    List<PartyDetailProjection> findPartyDetailsByLeaderId(@Param("leaderId") Long leaderId);

    @Query(value = """
    SELECT p.party_id AS partyId, p.party_name AS partyName,
           op.plan_name AS planName, l.reliability_score AS leaderReliability,
           (SELECT COUNT(*) FROM party_member pm WHERE pm.party_id = p.party_id) + 1 AS memberCount,
           p.party_status AS partyStatus
    FROM party p
    JOIN ott_plan op ON p.ott_plan_id = op.ott_plan_id
    JOIN member l ON p.leader_id = l.member_id
    JOIN party_member pm ON pm.party_id = p.party_id
    WHERE pm.member_id = :memberId
    """, nativeQuery = true)
    List<PartyDetailProjection> findPartyDetailsByMemberId(@Param("memberId") Long memberId);
}