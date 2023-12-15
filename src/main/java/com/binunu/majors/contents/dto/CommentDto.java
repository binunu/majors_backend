package com.binunu.majors.contents.dto;

import com.binunu.majors.membership.dto.MemberProfileDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentDto {
    @Getter
    private static int num=1;
    private int id;
    private MemberProfileDto from;
    @Transient
    private String to; //게시글id? or 댓글id
    private String content;
    private String createdAt;
    private List<ReplyDto> replies;
    private List<String> sympathy;

    public static void numbering(){
        ++num;
    }
}
