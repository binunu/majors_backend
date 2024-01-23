package com.binunu.majors.contents.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleInfo {
    private String id;
    private String title;
    private String content;
    private String middleMajor;
    private String boardType;
    private String subject;
    private int goods;
    private int commentCnt;
}
