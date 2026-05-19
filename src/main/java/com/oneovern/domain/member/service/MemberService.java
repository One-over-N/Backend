package com.oneovern.domain.member.service;

import com.oneovern.domain.member.converter.MemberConverter;
import com.oneovern.domain.member.dto.MemberReqDto;
import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public MemberResDto.Join join(MemberReqDto.Join dto) {
        //비밀번호 암호화
        String encodedPassword=passwordEncoder.encode(dto.password());

        //dto->member 엔티티
        Member newMember= MemberConverter.toMember(dto, encodedPassword);

        //DB 저장
        Member savedMember=memberRepository.save(newMember);

        //member 엔티티->dto
        return MemberConverter.toJoinResDto(savedMember);
    }
}
