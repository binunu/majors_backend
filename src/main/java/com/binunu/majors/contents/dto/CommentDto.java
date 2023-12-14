package com.binunu.majors.contents.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentDto {
    @Id
    private String id;
    private String from; //작성자id
    private String to; //게시글id? or 댓글id
    private String content;
    private String uploadDate;
    private List<CommentDto> replies;
}
