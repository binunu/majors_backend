package com.binunu.majors.membership.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Setter
@Getter
@ToString
@Document
@NoArgsConstructor
public class MemberProfileDto {
    private String email;
    private String nickname;
    private String major;
    private String middleMajor;
    private String graduate;
    private String profile;

    public MemberProfileDto(MemberDto member){
        this.email=member.getEmail();
        this.nickname=member.getNickname();
        this.graduate=member.getGraduate();
        this.middleMajor = member.getMiddleMajor();
        this.profile=member.getProfile();
        this.major=member.getMajor();
    }
}