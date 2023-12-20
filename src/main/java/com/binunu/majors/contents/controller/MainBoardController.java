package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.ArticleInfo;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.service.MainBoardService;
import com.binunu.majors.membership.service.MemberActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class MainBoardController {
    private final MainBoardService mainBoardService;
    private final MemberActionService memberActionService;
    private final ModelMapper modelMapper;
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
    //리스트형 게시글 목록
    @GetMapping("/article/list/{type}/{category}/{page}") //type:study, job, community
    public ResponseEntity<Map<String,Object>> getArticleList(@PathVariable("type") String boardType,
                                                         @PathVariable("category") String middleMajor,
                                                         @PathVariable("page") int page){
        Map<String,Object> res = null;
        log.info(middleMajor);
        try{
            if(middleMajor.equals("entire")){
                res = mainBoardService.getArticleListByType(boardType,page,10);
            }else{
                res = mainBoardService.getArticleListByTypeAndMajor(boardType,middleMajor,page,10);
            }
            List<ArticleInfo> newList = new ArrayList<>();
            for(Article a : (List<Article>)res.get("list")){
                ArticleInfo aInfo = modelMapper.map(a,ArticleInfo.class);
                aInfo.setCommentCnt(a.getComments().size());
                newList.add(aInfo);
            }
            res.put("list",newList);
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/article/peed/{type}/{category}/{page}") //type:study, job, community
    public ResponseEntity<Map<String,Object>> getArticleListForPeed(@PathVariable("type") String boardType,
                                                               @PathVariable("category") String middleMajor,
                                                               @PathVariable("page") int page){
        Map<String,Object> res = null;
        try{
            if(middleMajor.equals("entire")){
                res = mainBoardService.getArticleListByType(boardType,page,5);
            }else{
                res = mainBoardService.getArticleListByTypeAndMajor(boardType,middleMajor,page,5);
            }
            return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
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

    @DeleteMapping("/delete/article/{article-id}")
    public ResponseEntity<String> removeArticleItem(@PathVariable("article-id") String articleId){
        try{
            log.info("articleId:"+articleId);
            mainBoardService.removeArticle(articleId);
            return new ResponseEntity<String>("게시글이 삭제되었습니다!",HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/comment/{article-id}/{comment-id}")
    public ResponseEntity<Article> removeArticleItem(@PathVariable("article-id") String articleId, @PathVariable("comment-id") int commentId){
        try{
            log.info("댓글삭제id:"+commentId);
            Article article= mainBoardService.removeComment(articleId,commentId);
            return new ResponseEntity<Article>(article,HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/delete/reply/{article-id}/{comment-id}/{reply-id}")
    public ResponseEntity<Article> removeArticleItem(@PathVariable("article-id") String articleId, @PathVariable("comment-id") int commentId,@PathVariable("reply-id") int replyId){
        try{
            log.info("답글삭제id:"+replyId);
            Article article = mainBoardService.removeReply(articleId,commentId,replyId);
            return new ResponseEntity<Article>(article,HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);

        }
    }



}
