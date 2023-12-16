package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article,String> {
}
