package com.binunu.majors.contents.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "article")
public class ArticleDto {
    @Id
    private String id;
    private String title;
    private String content;
    private String writer;
    private String boardType;
    private String subject;
    private String middleMajor;
    private String uploadDate;
    private List<String> goods;
    private List<String> bads;
    //댓글추가
}
