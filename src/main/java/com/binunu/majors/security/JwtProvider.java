package com.binunu.majors.security;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.net.Authenticator;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${EXP_TIME}")
    private int EXP_TIME;

    //토큰생성(로그인시 호출)
    public String createToken(String subject, Authentication authentication){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //서명알고리즘선택
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY); //시크릿키를 사용해서 문자열->바이트 배열로 파싱
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName()); //파싱된 시크릿 키를 기반으로 서명에 사용할 key객체 생성

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder() //토큰 생성
                .setSubject(subject) //토큰의 주제. 어떤 정보에 대해 발급되었는지(email,id등)
                .claim("auth", roles)
                .signWith(signingKey, signatureAlgorithm) //서명키와 서명알고리즘
                .setExpiration(new Date(System.currentTimeMillis()+EXP_TIME)) //유효기간 설정
                .compact();
    }

    //토큰을 받아 클레임 가져오기
    public Claims getClaim(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    //토큰이 유효한지 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    //유효한 토큰을 받아 인증된 객체를 생성
    public Authentication getAuthentication(String token){
        Claims claims = getClaim(token);
        if(claims.get("auth")==null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

}
