package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import jdk.jshell.spi.ExecutionControlProvider;

import java.util.List;
import java.util.Map;

public interface MainBoardService {
    Article createArticle(Article article) throws Exception;
    Article modifyArticle(Article article) throws Exception;
    Map<String,Object> getArticleListByType(String boardType, int page, int cnt) throws Exception;
    Map<String,Object> getArticleListByTypeAndMajor(String boardType, String middleMajor, int page, int cnt) throws Exception;
    Article getArticleDetail(String id) throws Exception;
    Map<String,Object> createComment(CommentDto commentDto) throws Exception;
    Map<String,Object> createReply(ReplyDto replyDto) throws Exception;
    void removeArticle(String articleId) throws Exception;
    Article removeComment(String articleId, int commentId) throws Exception;
    Article removeReply(String articleId, int commentId, int replyId) throws Exception;


}
