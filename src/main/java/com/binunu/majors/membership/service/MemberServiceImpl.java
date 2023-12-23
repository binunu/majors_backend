package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.CommentInfo;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.security.JwtProvider;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Member getMemberByEmail(String email) throws Exception {
        Optional<Member> oMember = memberRepository.findByEmail(email);
        return oMember.orElse(null);
    }

    @Override
    public Member getMemberByNickname(String nickname) throws Exception {
        Optional<Member> oMember = memberRepository.findByNickname(nickname);
        return oMember.orElse(null);
    }

    @Override
    public void join(Member member) throws Exception {
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);

        member.setArticles(new ArrayList<String>());
        member.setComments(new ArrayList<CommentInfo>());
//        member.setReplys(new ArrayList<CommentInfo>());
        member.setScraps(new ArrayList<String>());
        member.setGoods(new ArrayList<String>());
        member.setBads(new ArrayList<String>());
//        member.setNotifications(0);

        List<String> role = new ArrayList<String>();
        role.add("USER");
        member.setRoles(role);

        memberRepository.save(member);
    }

    @Override
    public String login(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtProvider.createToken(email, authentication);

    }

    @Override
    public Member getCurrentMember() throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        return getMemberByEmail(email);
    }


}
