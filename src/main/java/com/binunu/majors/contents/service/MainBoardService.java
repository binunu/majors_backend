package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;

public interface MainBoardService {
    Article createArticle(Article article) throws Exception;
    Article getArticleDetail(String id) throws Exception;
    Article createComment(CommentDto commentDto) throws Exception;
    Article createReply(ReplyDto replyDto) throws Exception;
}
