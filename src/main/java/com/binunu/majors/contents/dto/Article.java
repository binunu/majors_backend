package com.binunu.majors.contents.dto;

import com.binunu.majors.membership.dto.MemberInfoDto;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "article")
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private MemberInfoDto writer;
    private String boardType;
    private String subject;
    private String middleMajor;
    private int goods;
    private int bads;
    private List<Reaction> reactions;
    private List<String> scraps;
    private List<CommentDto> comments;
    private boolean isDeleted;
//    @CreatedDate
    private String createdAt;
//    @LastModifiedDate
    private String modifiedAt;

}
