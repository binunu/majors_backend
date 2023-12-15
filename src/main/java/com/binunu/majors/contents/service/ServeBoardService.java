package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.Major;

import java.util.List;

public interface ServeBoardService {
    List<Major> getDistinctLargeMajor() throws Exception;
    List<Major> getDistinctMiddleMajor(String large) throws Exception;
    List<Major> getDistinctMiddleMajor() throws Exception;
    List<Major> getDistinctSmallMajor(String middle) throws Exception;

    Article bookmark(String articleId) throws Exception;
    Article sympathy(String articleId,int commentId) throws Exception;
    Article sympathy(String articleId,int commentId, int replyId) throws Exception;

}
