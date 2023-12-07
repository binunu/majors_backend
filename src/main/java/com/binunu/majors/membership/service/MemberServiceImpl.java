package com.binunu.majors.membership.service;

import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;


    @Override
    public MemberDto getMemberByEmail(String email) throws Exception {
        Optional<MemberDto> oMember = memberRepository.findByEmail(email);
        return oMember.orElse(null);
    }

    @Override
    public MemberDto getMemberByNickname(String nickname) throws Exception {
        Optional<MemberDto> oMember = memberRepository.findByNickname(nickname);
        return oMember.orElse(null);
    }

    @Override
    public void join(MemberDto memberDto) throws Exception {
        String encodePassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodePassword);
        //각 객체 만들고 추후에 추가.ㅇ
//        memberDto.setArticles(0);
//        memberDto.setComments(0);
//        memberDto.setScraps(0);
//        memberDto.setNotifications(0);
        List<String> role = new ArrayList<String>();
        role.add("USER");
        memberDto.setRoles(role);

        memberRepository.save(memberDto);
    }

    @Override
    public String login(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtProvider.createToken(email, authentication);

    }


}
