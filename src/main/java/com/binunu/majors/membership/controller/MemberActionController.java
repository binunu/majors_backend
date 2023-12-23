package com.binunu.majors.membership.controller;

import com.binunu.majors.contents.dto.ArticleInfo;
import com.binunu.majors.membership.service.MemberActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member")
public class MemberActionController {
    private final MemberActionService memberActionService;
    @GetMapping("/log/write/{page}") //글
    public ResponseEntity<Map<String,Object>> getLogArticle(@PathVariable("page") int page){
        try{
            Map<String,Object> res = memberActionService.getLogArticle(page,7);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/log/scrap/{page}") //스크랩
    public ResponseEntity<Map<String,Object>> getLogScrap(@PathVariable("page") int page) {
        try {
            Map<String,Object> res = memberActionService.getLogScrap(page,7);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/log/good/{page}") //좋아요
    public ResponseEntity<Map<String,Object>> getLogGoods(@PathVariable("page") int page) {
        try {
            Map<String,Object> res = memberActionService.getLogGoods(page,7);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/log/bad/{page}") //싫어요
    public ResponseEntity<Map<String,Object>> getLogBads(@PathVariable("page") int page) {
        try {
            Map<String,Object> res = memberActionService.getLogBads(page,7);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/log/comment/{page}") //댓글, 답글
    public ResponseEntity<Map<String,Object>> getLogComments(@PathVariable("page") int page) {
        try {
            Map<String,Object> res = memberActionService.getLogComments(page,3);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
}
