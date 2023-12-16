package com.binunu.majors.membership.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfoDto {
    private String id;
    private String title;
    private String major;
    private String subject;
    private int goodCnt;
    private int commentCnt;
}
