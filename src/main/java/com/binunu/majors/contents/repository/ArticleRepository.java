package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {
    Page<Article> findByBoardTypeAndIsDeletedFalse(PageRequest pageRequest,String boardType)throws Exception;
    Page<Article> findByBoardTypeAndMiddleMajorAndIsDeletedFalse(PageRequest pageRequest, String boardType, String middleMajor)throws Exception;
}
