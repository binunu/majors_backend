package com.binunu.majors.contents.dto;

import com.binunu.majors.membership.dto.MemberProfileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
    @Id
    private String id;
    private MemberProfileDto from;
    @Transient
    private String to; //게시글id? or 댓글id
    private String content;
    private String createdAt;
    private List<CommentDto> replies;
    private List<String> sympathy;
}
