package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.ArticleDto;
import com.binunu.majors.contents.dto.CommentDto;
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
    @PostMapping("/article/write")
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
    public ResponseEntity<Map<String,Object>> getArticleDetail(@PathVariable(value = "id") String id){
        try{
            Map<String,Object> map = mainBoardService.getArticleDetail(id);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment/write")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto){
        try{
            mainBoardService.createComment(commentDto);
            return new ResponseEntity<String>("성공", HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping("/reply/write")
//    public ResponseEntity<Map<String,Object>> writeComment(@RequestBody CommentDto commentDto){
//        try{
//            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//        }catch (Exception e){
//            log.info(e.getMessage());
//            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
//        }
//    }


}
