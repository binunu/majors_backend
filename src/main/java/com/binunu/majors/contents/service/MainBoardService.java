package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.ArticleDto;
import com.binunu.majors.contents.dto.CommentDto;

import java.util.Map;

public interface MainBoardService {
    ArticleDto createArticle(ArticleDto articleDto) throws Exception;
    ArticleDto getArticleDetail(String id) throws Exception;
    ArticleDto createComment(CommentDto commentDto) throws Exception;
}
