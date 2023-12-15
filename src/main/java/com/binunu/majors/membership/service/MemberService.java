package com.binunu.majors.membership.service;

import com.binunu.majors.membership.dto.Member;

public interface MemberService {
    public Member getMemberByEmail(String email) throws Exception;
    public Member getMemberByNickname(String nickname) throws Exception;
    public void join(Member member) throws Exception;
    public String login(String email, String password) throws Exception;
    Member getCurrentMember() throws Exception;

}
