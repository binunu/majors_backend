package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.Article;

import java.util.List;
import java.util.Map;

public interface MemberActionService {
    //저장하기
    void createArticle(String articleId) throws Exception;
    void bookmark(String articleId) throws Exception;
    void writeComment(String articleId,int commentId) throws Exception;
    void writeReply(String articleId,int commentId, int replyId) throws Exception;
    void reaction(String articleId,String state) throws Exception;

    //가져오기
    Map<String,Object> getLogArticle(int page,int cnt)throws Exception;
    Map<String,Object> getLogScrap(int page,int cnt)throws Exception;
    Map<String,Object> getLogGoods(int page,int cnt)throws Exception;
    Map<String,Object> getLogBads(int page,int cnt)throws Exception;
    Map<String,Object> getLogComments(int page,int cnt)throws Exception;

    //삭제하기
    void removeArticle(String articleId)throws Exception;

    void removeComment(String articleId, int commentId)throws Exception;
    void removeReply(String articleId, int commentId, int replyId)throws Exception;

}
