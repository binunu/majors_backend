package com.binunu.majors.contents.dto;

import com.binunu.majors.membership.dto.MemberProfileDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
    @Getter
    private static int num=1;
    private int id;
    private MemberProfileDto from;
    @Transient
    private String articleId; //게시글id? or 댓글id
    @Transient
    private int replyId;

    private String content;
    private String createdAt;
    private List<String> sympathy;

    public static void numbering(){
        num++;
    }
}
