package com.oneovern.domain.member.repository;

import com.oneovern.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    //이메일 변경 시 중복 검사
    boolean existsByEmail(String email);
}
