package com.binunu.majors.membership.service;

import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<MemberDto> getMemberByEmail(String email) throws Exception {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Optional<MemberDto> getMemberByNickname(String nickname) throws Exception {
        return memberRepository.findByNickname(nickname);
    }

    @Override
    public void join(MemberDto memberDto) throws Exception {
        memberRepository.save(memberDto);
    }

}
