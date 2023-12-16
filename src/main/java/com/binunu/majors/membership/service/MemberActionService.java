package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.Article;

import java.util.List;

public interface MemberActionService {
    void createArticle(String articleId) throws Exception;
    void bookmark(String articleId) throws Exception;
    void sympathy(String articleId,int commentId, int replyId) throws Exception;
    void sympathy(String articleId,int commentId) throws Exception;
}
