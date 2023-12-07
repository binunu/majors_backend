package com.binunu.majors.membership.repository;

import com.binunu.majors.membership.dto.MemberDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<MemberDto, String> {
    public Optional<MemberDto> findByEmail(String email) throws UsernameNotFoundException;
    public Optional<MemberDto> findByNickname(String nickname) throws Exception;
}
