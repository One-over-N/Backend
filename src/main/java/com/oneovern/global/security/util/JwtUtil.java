package com.oneovern.global.security.util;

import com.oneovern.global.security.entity.AuthMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;
    private final Duration refreshExpiration;

    public JwtUtil(
            @Value("${jwt.token.secretKey}") String secret,
            @Value("${jwt.token.expiration.access}") Duration accessExpiration,
            @Value("${jwt.token.expiration.refresh}") Duration refreshExpiration
    ) {
      this.secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
      this.accessExpiration=accessExpiration;
      this.refreshExpiration=refreshExpiration;
    }

    //토큰 생성
    private String createToken(AuthMember member, Duration expiration) {
        Instant now = Instant.now();

        //인가 정보
        String authorities=member.getAuthorities().stream() //여러 권한 확인
                .map(GrantedAuthority::getAuthority) //문자열 리스트로 변환
                .collect(Collectors.joining(",")); //텍스트 파일로 변환

        return Jwts.builder()
                .subject(member.getUsername()) //user 이메일을 subject로
                .claim("role", authorities) //권한
                .claim("email", member.getUsername()) //이메일
                .issuedAt(Date.from(now)) //발급 일시
                .expiration(Date.from(now.plus(expiration))) //유효 일시
                .signWith(secretKey)
                .compact();

    }

    //토근 정보 가져오기
    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60) //오차 인정
                .build()
                .parseSignedClaims(token);
    }

    //AccessToken 생성
    public String createAccessToken(AuthMember member){
        return createToken(member, accessExpiration);
    }

    //RefreshToken 생성
    public String createRefreshToken(AuthMember member){
        return createToken(member, refreshExpiration);
    }

    //토큰에서 이메일 추출
    public String getEmail(String token){
        try{
            return getClaims(token).getPayload().getSubject();
        }catch(JwtException e){
            return null;
        }
    }

    //토큰 유효성 확인
    public boolean isValid(String token){
        try {
            getClaims(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    //토큰 만료 시간 추출
    public Long getExpiration(String token){
        try {
            return getClaims(token).getPayload().getExpiration().getTime();
        }catch(JwtException e){
            return null;
        }
    }

}
