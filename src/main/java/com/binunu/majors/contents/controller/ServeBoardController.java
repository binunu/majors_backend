package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.Major;
import com.binunu.majors.contents.service.ServeBoardService;
import com.binunu.majors.membership.service.MemberActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("contents")
public class ServeBoardController {

    private final ServeBoardService serveBoardService;
    private final MemberActionService memberActionService;
    @GetMapping("major-list/large")
    public ResponseEntity<List<Major>> getDistinctLargeMajor() {
        try {
            List<Major> list = serveBoardService.getDistinctLargeMajor();
            return new ResponseEntity<List<Major>>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<List<Major>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("major-list/middle")
    public ResponseEntity<List<Major>> getDistinctMiddleMajor(@RequestParam(value = "large", required = false) String large) {
            List<Major> list = null;
        try {
            if(large!=null){
                list = serveBoardService.getDistinctMiddleMajor(large);
            }else{
                list = serveBoardService.getDistinctMiddleMajor();
            }
            return new ResponseEntity<List<Major>>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<List<Major>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("major-list/small")
    public ResponseEntity<List<Major>> getDistinctSmallMajor(@RequestParam("middle") String middle) {
        try {
            List<Major> list = serveBoardService.getDistinctSmallMajor(middle);
            return new ResponseEntity<List<Major>>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<List<Major>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/bookmark/{id}")
    public ResponseEntity<Article> bookmark(@PathVariable("id") String articleId) {
        try {
            //게시글 북마크리스트 갱신
            Article article = serveBoardService.bookmark(articleId);
            //멤버 북마크리스트 갱신
            memberActionService.bookmark(articleId);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sympthy/{id}/{commentId}")
    public ResponseEntity<Article> sympathy(@PathVariable("id") String articleId,@PathVariable("commentId") int commentId) {
        try {
            //댓글 공감한 유저리스트 갱신
            Article article = serveBoardService.sympathy(articleId,commentId);
            //멤버 공감한 댓글
            memberActionService.sympathy(articleId,commentId);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sympthy/{id}/{commentId}/{replyId}")
    public ResponseEntity<Article> sympathy(@PathVariable("id") String articleId,@PathVariable("commentId") int commentId,@PathVariable("replyId") int replyId) {
        try {
            //댓글 공감한 유저리스트 갱신
            Article article = serveBoardService.sympathy(articleId,commentId,replyId);
            //멤버 공감한 댓글
            memberActionService.sympathy(articleId,commentId,replyId);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }



}
