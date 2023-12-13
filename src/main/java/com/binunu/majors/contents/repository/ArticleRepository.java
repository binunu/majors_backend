package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.ArticleDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<ArticleDto,String> {
}
