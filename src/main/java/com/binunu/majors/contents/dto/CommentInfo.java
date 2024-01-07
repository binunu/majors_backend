package com.binunu.majors.contents.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfo {
    private Date createdAt;
    private String articleId;
    private int commentId;
    private String articleTitle;
    private String articleBoardType;
    private String type;
    private String content;
    private int sympathyCnt;
    //type이 comment일때만
    private int replyId;
    private int replyCnt;
    private boolean isDeleted;

    public CommentInfo(String articleId, int commentId){
        this.type="comment";
        this.articleId = articleId;
        this.commentId = commentId;
        this.createdAt = new Date();
    }
    public CommentInfo(String articleId, int commentId, int replyId){
        this.type="reply";
        this.articleId = articleId;
        this.commentId = commentId;
        this.replyId = replyId;
        this.createdAt = new Date();
    }
}
