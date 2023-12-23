package com.binunu.majors.membership.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private String email;
    private String nickname;
    private String major;
    private String middleMajor;
    private String graduate;
    private String profile;
}