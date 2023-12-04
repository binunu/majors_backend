package com.binunu.majors.membership.service;

import com.binunu.majors.membership.dto.MemberDto;

import java.util.Optional;

public interface MemberService {
    public Optional<MemberDto> getMemberByEmail(String email) throws Exception;
    public Optional<MemberDto> getMemberByNickname(String nickname) throws Exception;
    public void join(MemberDto memberDto) throws Exception;

}
