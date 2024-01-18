package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.membership.dto.Member;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MainBoardService {
    Article createArticle(Article article) throws Exception;
    Article modifyArticle(Article article) throws Exception;
    Map<String,Object> getArticleListByType(String boardType, int page, int cnt) throws Exception;
    Map<String,Object> getArticleListByTypeAndMajor(String boardType, String middleMajor, int page, int cnt) throws Exception;
    List<Article> getArticleListOnGoods() throws Exception;
    List<Article> getArticleListOnComments() throws Exception;
    List<Article> getArticleListOnRecency() throws Exception;
    List<Article> getArticleListOnMajor() throws Exception;
    List<Member> getArticleListOnRank() throws Exception;

    Article getArticleDetail(String id) throws Exception;
    Map<String,Object> createComment(CommentDto commentDto) throws Exception;
    Map<String,Object> createReply(ReplyDto replyDto) throws Exception;
    void removeArticle(String articleId) throws Exception;
    Article removeComment(String articleId, int commentId) throws Exception;
    Article removeReply(String articleId, int commentId, int replyId) throws Exception;


}
