package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryTemp {
    private final MongoTemplate mongoTemplate;

    public ArticleDto getArticleById(String articleId) {
        Query query = new Query(Criteria.where("_id").is(articleId));
        return mongoTemplate.findOne(query, ArticleDto.class);
    }
}
