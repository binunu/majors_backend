package com.binunu.majors.security;

import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> oMember = memberRepository.findByEmail(username);
        if(oMember.isPresent() && !oMember.get().isDeleted()){
            return createUserDetails(oMember.get());
        }else{
            throw new UsernameNotFoundException("회원정보가 일치하지 않습니다.");
        }
    }
    private UserDetails createUserDetails(Member member){
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
}
