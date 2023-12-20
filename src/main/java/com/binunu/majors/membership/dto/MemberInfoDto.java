package com.binunu.majors.membership.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MemberInfoDto {
    private String email;
    private String nickname;
    private String major;
    private String middleMajor;
    private String graduate;
    private String profile;

    public MemberInfoDto(Member member){
        this.email=member.getEmail();
        this.nickname=member.getNickname();
        this.graduate=member.getGraduate();
        this.middleMajor = member.getMiddleMajor();
        this.profile=member.getProfile();
        this.major=member.getMajor();
    }
}