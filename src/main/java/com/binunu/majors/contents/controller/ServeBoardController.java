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
import java.util.Map;

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
//            memberActionService.sympathy(articleId,commentId);
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
//            memberActionService.sympathy(articleId,commentId,replyId);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/reaction/{id}")
        public ResponseEntity<Map<String, Object>> reaction(@PathVariable("id") String articleId, @RequestBody Map<String,String> body) {
            try {
                String reactionType = body.get("reactionType");
                //내려보내줄거 ? article의 좋아요수, 싫어요수, 유저의 리액션 상태
                Map<String, Object> res =serveBoardService.reaction(articleId,reactionType);
                //유저의 리액션 list에 아티클 추가
                memberActionService.reaction(articleId,(String)res.get("state"));
                return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
            } catch (Exception e) {
                log.info(e.getMessage());
                return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
            }
        }



}
