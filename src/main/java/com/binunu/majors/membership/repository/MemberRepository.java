package com.binunu.majors.membership.repository;

import com.binunu.majors.membership.dto.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    public Optional<Member> findByEmail(String email) throws UsernameNotFoundException;
    public Optional<Member> findByNickname(String nickname) throws Exception;
}
