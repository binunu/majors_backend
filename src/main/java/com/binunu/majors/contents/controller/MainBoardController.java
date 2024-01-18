package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.ArticleInfo;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.service.MainBoardService;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberInfoDto;
import com.binunu.majors.membership.service.MemberActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    @PostMapping("/modify/article")
    public ResponseEntity<String> modifyArticle(@RequestBody Article articleDto){
        try{
            Article article = mainBoardService.modifyArticle(articleDto);
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
    // ** home **
    //추천
    @GetMapping("/article/list/goods")
    public ResponseEntity<List<ArticleInfo>> getArticleListOnGoods(){
        try{
            List<Article> list = mainBoardService.getArticleListOnGoods();
            List<ArticleInfo> newList = new ArrayList<>();
            for(Article a : list){
                ArticleInfo aInfo = modelMapper.map(a,ArticleInfo.class);
                aInfo.setCommentCnt(a.getComments().size());
                newList.add(aInfo);
            }
            return new ResponseEntity<List<ArticleInfo>>(newList,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<List<ArticleInfo>>(HttpStatus.BAD_REQUEST);
        }
    }
    //댓글
    @GetMapping("/article/list/comments")
    public ResponseEntity<List<ArticleInfo>> getArticleListOnComments(){
        try{
            List<Article> list = mainBoardService.getArticleListOnComments();
            List<ArticleInfo> newList = new ArrayList<>();
            for(Article a : list){
                ArticleInfo aInfo = modelMapper.map(a,ArticleInfo.class);
                aInfo.setCommentCnt(a.getComments().size());
                newList.add(aInfo);
            }
            return new ResponseEntity<List<ArticleInfo>>(newList,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<List<ArticleInfo>>(HttpStatus.BAD_REQUEST);
        }
    }
    //최신
    @GetMapping("/article/list/recency")
    public ResponseEntity<List<ArticleInfo>> getArticleListOnRecency(){
        try{
            List<Article> list = mainBoardService.getArticleListOnRecency();
            List<ArticleInfo> newList = new ArrayList<>();
            for(Article a : list){
                ArticleInfo aInfo = modelMapper.map(a,ArticleInfo.class);
                aInfo.setCommentCnt(a.getComments().size());
                newList.add(aInfo);
            }
            return new ResponseEntity<List<ArticleInfo>>(newList,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<List<ArticleInfo>>(HttpStatus.BAD_REQUEST);
        }
    }


    //전공
    @GetMapping("/article/list/user/major")
    public ResponseEntity<List<ArticleInfo>> getArticleListOnMajor(){
        try{
            List<Article> list = mainBoardService.getArticleListOnMajor();
            List<ArticleInfo> newList = new ArrayList<>();
            for(Article a : list){
                ArticleInfo aInfo = modelMapper.map(a,ArticleInfo.class);
                aInfo.setCommentCnt(a.getComments().size());
                newList.add(aInfo);
            }
            return new ResponseEntity<List<ArticleInfo>>(newList,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<List<ArticleInfo>>(HttpStatus.BAD_REQUEST);
        }
    }
    //랭킹
    @GetMapping("/article/list/user/rank")
    public ResponseEntity<List<MemberInfoDto>> getArticleListOnRank(){
        try{
            List<Member> list = mainBoardService.getArticleListOnRank();
            List<MemberInfoDto> newList = new ArrayList<>();
            for(Member m : list){
                MemberInfoDto mInfo = modelMapper.map(m,MemberInfoDto.class);
                newList.add(mInfo);
            }
            return new ResponseEntity<List<MemberInfoDto>>(newList,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<List<MemberInfoDto>>(HttpStatus.BAD_REQUEST);
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
            Map<String,Object> map = mainBoardService.createComment(commentDto);
            Article article = (Article)map.get("article");
            memberActionService.writeComment(article.getId(), (int)map.get("commentId"));

            return new ResponseEntity<Article>(article, HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/write/reply")
    public ResponseEntity<Article> writeComment(@RequestBody ReplyDto replyDto){
        try{
            log.info(replyDto.toString());
            Map<String,Object> map = mainBoardService.createReply(replyDto);
            Article article = (Article)map.get("article");
            memberActionService.writeReply(article.getId(), (int)map.get("commentId"),(int)map.get("replyId"));

            return new ResponseEntity<Article>(article, HttpStatus.OK);

        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/article/{article-id}")
    public ResponseEntity<Map<String,Object>> removeArticleItem(@PathVariable("article-id") String articleId){
        try{
            mainBoardService.removeArticle(articleId);
            //멤버에서도삭제
            memberActionService.removeArticle(articleId);
            return new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/comment/{article-id}/{comment-id}")
    public ResponseEntity<Article> removeArticleItem(@PathVariable("article-id") String articleId, @PathVariable("comment-id") int commentId){
        try{
            Article article= mainBoardService.removeComment(articleId,commentId);
            memberActionService.removeComment(articleId,commentId);
            return new ResponseEntity<Article>(article,HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/delete/reply/{article-id}/{comment-id}/{reply-id}")
    public ResponseEntity<Article> removeArticleItem(@PathVariable("article-id") String articleId, @PathVariable("comment-id") int commentId,@PathVariable("reply-id") int replyId){
        try{
            Article article = mainBoardService.removeReply(articleId,commentId,replyId);
            log.info("리플삭제들어왔나?1");
            memberActionService.removeReply(articleId,commentId,replyId);
            return new ResponseEntity<Article>(article,HttpStatus.OK);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<Article>(HttpStatus.BAD_REQUEST);

        }
    }



}
