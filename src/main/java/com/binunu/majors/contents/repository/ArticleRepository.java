package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.Article;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {
    Page<Article> findByBoardTypeAndIsDeletedFalse(PageRequest pageRequest,String boardType)throws Exception;
    Page<Article> findByBoardTypeAndMiddleMajorAndIsDeletedFalse(PageRequest pageRequest, String boardType, String middleMajor)throws Exception;
    @Aggregation(pipeline = {
            "{ $match: { comments: { $exists: true, $ne: [] }  , isDeleted: false } }",
            "{ $project: { id: 1, title: 1, content: 1, writer: 1, boardType: 1, subject: 1, middleMajor: 1, goods: 1, bads: 1, reactions: 1, scraps: 1, comments: 1, isDeleted: 1, createdAt: 1, modifiedAt: 1, commentsSize: { $size: \"$comments\" } } }",
            "{ $sort: { commentsSize: -1 } }",
            "{ $limit: 5 }"
    })
    List<Article> findTop5ByOrderByCommentCountDesc() throws Exception;

    // 홈 "우리전공 핫한 게시글"
    List<Article> findAllByMiddleMajorAndIsDeletedFalse(PageRequest pageRequest,String major)throws Exception;

    @Query("{'boardType': ?0, 'isDeleted':false, '$or': [{'content': {$regex : ?1, $options: 'i'}}, {'title': {$regex : ?1, $options: 'i'}}]}")
    Page<Article> findAllByBoardTypeAndContent(String type, String word,PageRequest pageRequest)throws Exception;

    Page<Article> findAllByIsDeletedFalse(PageRequest pageRequest)throws Exception;

}
