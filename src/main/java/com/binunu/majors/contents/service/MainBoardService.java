package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.ArticleDto;

import java.util.Map;

public interface MainBoardService {
    ArticleDto createArticle(ArticleDto articleDto) throws Exception;
    Map<String,Object> getArticleDetail(String id) throws Exception;
}
