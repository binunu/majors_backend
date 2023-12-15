package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.service.MainBoardService;
import com.binunu.majors.membership.service.MemberActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class MainBoardController {
    private final MainBoardService mainBoardService;
    private final MemberActionService memberActionService;
    @PostMapping("/write/article")
    public ResponseEntity<String> createArticle(@RequestBody Article articleDto){
        try{
            Article article = mainBoardService.createArticle(articleDto);
            memberActionService.createArticle(article.getId());
            return new ResponseEntity<String>(article.getId(), HttpStatus.OK);

        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/article/detail/{id}")
    public ResponseEntity<Article> getArticleDetail(@PathVariable("id") String id){
        try{
            Article article = mainBoardService.getArticleDetail(id);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/write/comment")
    public ResponseEntity<Article> createComment(@RequestBody CommentDto commentDto){
        try{
            Article article = mainBoardService.createComment(commentDto);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/write/reply")
    public ResponseEntity<Article> writeComment(@RequestBody ReplyDto replyDto){
        try{
            Article article = mainBoardService.createReply(replyDto);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }


}
