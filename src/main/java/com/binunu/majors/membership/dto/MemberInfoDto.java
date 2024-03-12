package com.binunu.majors.membership.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private String email;
    private String name;
    private String nickname;
    private String major;
    private String middleMajor;
    private String largeMajor;
    private String graduated;
    private String profile;
    private String joinedAt;
}