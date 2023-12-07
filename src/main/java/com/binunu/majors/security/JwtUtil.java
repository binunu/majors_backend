package com.binunu.majors.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {
    //현재 로그인된 멤버 아이디 리턴하기
    public static String getCurrentMemberEmail(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName()==null){
            throw new RuntimeException("SecurityContextHolder에 등록된 인증 정보가 없습니다.");
        }
        return authentication.getName();
    }
}
