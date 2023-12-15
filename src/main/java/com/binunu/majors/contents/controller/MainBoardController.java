package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.ArticleDto;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class MainBoardController {
    private final MainBoardService mainBoardService;
    @PostMapping("/write/article")
    public ResponseEntity<String> createArticle(@RequestBody ArticleDto articleDto){
        try{
            ArticleDto article = mainBoardService.createArticle(articleDto);
            return new ResponseEntity<String>(article.getId(), HttpStatus.OK);

        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/article/detail/{id}")
    public ResponseEntity<ArticleDto> getArticleDetail(@PathVariable("id") String id){
        try{
            ArticleDto articleDto = mainBoardService.getArticleDetail(id);
            return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<ArticleDto>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/write/comment")
    public ResponseEntity<ArticleDto> createComment(@RequestBody CommentDto commentDto){
        try{
            ArticleDto articleDto = mainBoardService.createComment(commentDto);
            return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<ArticleDto>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/write/reply")
    public ResponseEntity<ArticleDto> writeComment(@RequestBody ReplyDto replyDto){
        try{
            log.info(replyDto.toString());
            ArticleDto articleDto = mainBoardService.createReply(replyDto);
            return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<ArticleDto>(HttpStatus.BAD_REQUEST);
        }
    }


}
