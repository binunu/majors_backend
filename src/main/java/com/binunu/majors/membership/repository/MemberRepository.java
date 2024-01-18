package com.binunu.majors.membership.repository;

import com.binunu.majors.membership.dto.Member;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    public Optional<Member> findByEmail(String email) throws UsernameNotFoundException;
    public Optional<Member> findByNickname(String nickname) throws Exception;
    @Aggregation(pipeline = {
            "{ $match :  {middleMajor : ?0}}",
            "{ $project: { id: 1, nickname: 1, profile: 1 ,articlesCount: { $size: \"$articles\" }, commentsCount: { $size: \"$comments\" }, _id: 0 } }",
            "{ $addFields: { activityCount: { $sum: [\"$articlesCount\", \"$commentsCount\"] } } }",
            "{ $sort: {activityCount :  -1}}",
            "{ $limit: 5}"
    })
    public List<Member> findTop5ByOrderByActionDesc(String major) throws Exception;
}
