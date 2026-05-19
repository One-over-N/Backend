package com.oneovern.global.security.service;

import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.exception.MemberException;
import com.oneovern.domain.member.exception.code.MemberErrorCode;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member=memberRepository.findByEmail(username).orElseThrow(()->new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);

    }
}