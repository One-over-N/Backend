package com.oneovern.domain.member.service;

import com.oneovern.domain.member.converter.MemberConverter;
import com.oneovern.domain.member.dto.MemberReqDto;
import com.oneovern.domain.member.dto.MemberResDto;
import com.oneovern.domain.member.entity.Member;
import com.oneovern.domain.member.entity.ReliabilityHistory;
import com.oneovern.domain.member.exception.MemberException;
import com.oneovern.domain.member.exception.code.MemberErrorCode;
import com.oneovern.domain.member.repository.MemberRepository;
import com.oneovern.domain.member.repository.ReliabilityHistoryRepository;
import com.oneovern.global.security.entity.AuthMember;
import com.oneovern.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReliabilityHistoryRepository reliabilityHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    //회원가입
    @Transactional
    public MemberResDto.Join join(MemberReqDto.Join dto) {

        //이메일 중복 확인
        if(memberRepository.findByEmail(dto.email()).isPresent()){
            throw new MemberException(MemberErrorCode.ALREADY_EXIST_MEMBER);
        }

        //비밀번호 암호화
        String encodedPassword=passwordEncoder.encode(dto.password());

        //dto->member 엔티티
        Member newMember= MemberConverter.toMember(dto, encodedPassword);

        //DB 저장
        Member savedMember=memberRepository.save(newMember);

        //member 엔티티->dto
        return MemberConverter.toJoinResDto(savedMember);
    }

    //로그인
    @Transactional
    public MemberResDto.Login login(MemberReqDto.Login dto) {

        //이메일 확인
        Member member=memberRepository.findByEmail(dto.email())
                .orElseThrow(()->new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        //비밀번호 확인
        if(!passwordEncoder.matches(dto.password(),member.getPassword())){
            throw new MemberException(MemberErrorCode.INCORRECT_PASSWORD);
        }

        //member->authmember
        AuthMember authMember=new AuthMember(member);

        //accessToken 생성
        String accessToken=jwtUtil.createAccessToken(authMember);

        //refreshToken 생성
        String refreshToken=jwtUtil.createRefreshToken(authMember);

        //redis에 refreshToken 추가
        Long expiration= jwtUtil.getExpiration(refreshToken);

        if(expiration!=null) {
            redisTemplate.opsForValue().set(
                    "RT:" + member.getEmail(),
                    refreshToken,
                    expiration - System.currentTimeMillis(), // RT 만료시간만큼 유지
                    TimeUnit.MILLISECONDS
            );
        }
        //member 엔티티,토큰->dto
        return MemberConverter.toLoginResDto(member, accessToken, refreshToken);
    }

    //로그아웃
    public void logout(Member member) {

        String accessToken= (String)SecurityContextHolder.getContext().getAuthentication().getCredentials();

        //refreshToekn 삭제
        String rtKey="RT:"+member.getEmail();
        redisTemplate.delete(rtKey);

        //accessToekn 블랙리스트 등록
        Long expirationTime=jwtUtil.getExpiration(accessToken);

        if (expirationTime!=null){
            long now=System.currentTimeMillis();
            long remainTime=expirationTime-now;

            if (remainTime > 0) {
                redisTemplate.opsForValue().set(
                        "BL:"+accessToken,
                        "logout",
                        remainTime,
                        TimeUnit.MILLISECONDS
                );
            }
        }
    }

    //마이페이지 정보 조회
    public MemberResDto.MyPage getMyPageInfo(Member loginMember) {
        if (loginMember == null) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<ReliabilityHistory> limitedHistoryList = reliabilityHistoryRepository.findTop3ByMemberOrderByCreatedAtDesc(member);

        return MemberConverter.toMyPageResDto(member, limitedHistoryList);
    }

    //마이페이지 프로필 정보 수정 로직
    @Transactional
    public void updateProfile(Member loginMember, MemberReqDto.UpdateProfileDto request) {
        if (loginMember == null) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        //입력된 이메일이 기존 이메일과 다른 경우에만 중복 검사 실행
        if (!member.getEmail().equals(request.email())) {
            if (memberRepository.existsByEmail(request.email())) {
                // 기존 회원가입 시 쓰는 중복 에러코드(ALREADY_EXIST_MEMBER) 재활용
                throw new MemberException(MemberErrorCode.ALREADY_EXIST_MEMBER);
            }
        }

        //엔티티 데이터 변경
        member.updateProfile(request.nickname(), request.email());
    }

}
