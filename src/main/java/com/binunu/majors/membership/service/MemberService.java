package com.binunu.majors.membership.service;

import com.binunu.majors.membership.dto.MemberDto;

import java.util.Optional;

public interface MemberService {
    public MemberDto getMemberByEmail(String email) throws Exception;
    public MemberDto getMemberByNickname(String nickname) throws Exception;
    public void join(MemberDto memberDto) throws Exception;
    public String login(String email, String password) throws Exception;
    MemberDto getCurrentMember() throws Exception;

}
