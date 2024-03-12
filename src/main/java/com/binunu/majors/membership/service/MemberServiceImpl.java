package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.CommentInfo;
import com.binunu.majors.contents.service.CustomUtilService;
import com.binunu.majors.contents.service.FileService;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberInfoDto;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final FileService fileService;


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
        member.setProfile("basic_profile.png");
        member.setArticles(new ArrayList<String>());
        member.setComments(new ArrayList<CommentInfo>());
//        member.setReplys(new ArrayList<CommentInfo>());
        member.setScraps(new ArrayList<String>());
        member.setGoods(new ArrayList<String>());
        member.setBads(new ArrayList<String>());
//        member.setNotifications(0);
        member.setDeleted(false);
        member.setJoinedAt(CustomUtilService.changeDateToString());

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

    @Override
    public Member modifyMember(MemberInfoDto memberInfoDto, MultipartFile file) throws Exception {
        Member member = getCurrentMember();
        member.setNickname(memberInfoDto.getNickname());
        member.setLargeMajor(memberInfoDto.getLargeMajor());
        member.setMiddleMajor(memberInfoDto.getMiddleMajor());
        member.setMajor(memberInfoDto.getMajor());
        member.setGraduated(memberInfoDto.getGraduated());

        if(file!=null&&!file.isEmpty()){
            String fileName = fileService.fileUpload(file);
            member.setProfile(fileName);
        }

        return memberRepository.save(member);
    }

    @Override
    public void memberWithdrawal() throws Exception {
        Member member = getCurrentMember();
        member.setDeleted(true);
        memberRepository.save(member);
    }


}
