package com.binunu.majors.membership.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Setter
@Getter
@ToString
public class MemberProfileDto {
    private String id;
    private String email;
    private String nickname;
    private String major;
//    private String middleMajor;
    private String graduate;
    private String profile;

    public MemberProfileDto(MemberDto member){
        this.id=member.getId();
        this.email=member.getEmail();
        this.nickname=member.getEmail();
        this.graduate=member.getGraduate();
        this.profile=member.getProfile();
        this.major=member.getMajor();
    }
}
