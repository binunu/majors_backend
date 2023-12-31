package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.*;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleTemRepository;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberInfoDto;
import com.binunu.majors.membership.service.MemberService;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainBoardServiceImpl implements MainBoardService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final ArticleTemRepository articleTemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Article createArticle(Article article) throws Exception {
        Member mem = memberService.getCurrentMember();
        MemberInfoDto memberProfileDto = modelMapper.map(mem, MemberInfoDto.class);
        article.setComments(new ArrayList<CommentDto>());
        article.setWriter(memberProfileDto);
        article.setGoods(0);
        article.setBads(0);
        article.setDeleted(false);
        article.setReactions(new ArrayList<Reaction>());
        article.setScraps(new ArrayList<String>());

        return articleRepository.save(article);
    }

    @Override
    public Map<String,Object> getArticleListByType(String boardType, int page, int cnt) throws Exception {
        Map<String,Object> res = new HashMap<>();
        PageRequest pageRequest = PageRequest.of(page-1,cnt, Sort.by("_id").descending());
        Page<Article> articles = articleRepository.findByBoardTypeAndIsDeletedFalse(pageRequest, boardType);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(articles.getTotalElements());
        pageInfo.setCurPage(page);
        pageInfo.setAllPage(articles.getTotalPages());
        int start = ((page - 1) / 8) * 8 + 1;
        int end = start+8-1;
        if(end>pageInfo.getAllPage()) {
            end = pageInfo.getAllPage();
        }
        pageInfo.setStartPage(start);
        pageInfo.setEndPage(end);

        res.put("pageInfo",pageInfo);
        res.put("list",articles.getContent());

        return res;
    }
    @Override
    public Map<String,Object> getArticleListByTypeAndMajor(String boardType,String middleMajor, int page, int cnt) throws Exception {
        Map<String,Object> res = new HashMap<String,Object>();
        PageRequest pageRequest = PageRequest.of(page-1,cnt, Sort.by("_id").descending());
        Page<Article> articles = articleRepository.findByBoardTypeAndMiddleMajorAndIsDeletedFalse(pageRequest, boardType, middleMajor);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(articles.getTotalElements());
        pageInfo.setCurPage(page);
        pageInfo.setAllPage(articles.getTotalPages());
        int start = ((page - 1) / 8) * 8 + 1;
        int end = start+8-1;
        if(end>pageInfo.getAllPage()) {
            end = pageInfo.getAllPage();
        }
        pageInfo.setStartPage(start);
        pageInfo.setEndPage(end);

        res.put("pageInfo",pageInfo);
        res.put("list",articles.getContent());

        return res;
    }
    @Override
    public Article getArticleDetail(String id) throws Exception {
        return articleTemRepository.getArticleById(id);
    }

    @Override
    public Map<String,Object> createComment(CommentDto commentDto) throws Exception {
        Map<String,Object> map = new HashMap<>();
        commentDto.setReplies(new ArrayList<ReplyDto>()); //테스트필요

        Member member = memberService.getCurrentMember();
        MemberInfoDto memberProfileDto = modelMapper.map(member, MemberInfoDto.class);
        commentDto.setFrom(memberProfileDto); //작성자 기록
        //등록일 설정
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        commentDto.setCreatedAt(strDate);
        //공감
        commentDto.setSympathy(new ArrayList<String>());
        //id
        int commentId = CommentDto.getNum();
        commentDto.setId(commentId);
        CommentDto.numbering();

        //article update
        Article article = getArticleDetail(commentDto.getTo()); //to없애기
        List<CommentDto> comment = article.getComments();
        comment.add(commentDto);
        article.setComments(comment);

        map.put("article",articleRepository.save(article));
        map.put("commentId",commentId);

        return map;
    }

    @Override
    public Map<String,Object> createReply(ReplyDto replyDto) throws Exception {
        Map<String,Object> map = new HashMap<>();
        //등록일
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        replyDto.setCreatedAt(strDate);
        //공감수초기화
        replyDto.setSympathy(new ArrayList<String>());
        //답글작성자
        Member member = memberService.getCurrentMember();
        MemberInfoDto memberProfileDto = modelMapper.map(member, MemberInfoDto.class);
        replyDto.setFrom(memberProfileDto);
        //id넘버링
        int replyId = ReplyDto.getNum();
        replyDto.setId(replyId);
        ReplyDto.numbering();
        //article update
        Article article = getArticleDetail(replyDto.getArticleId());
        for(CommentDto c : article.getComments()){
            if(c.getId()==replyDto.getCommentId()){
                List<ReplyDto> list = c.getReplies();
                list.add(replyDto);
                c.setReplies(list);
                break;
            }
        }
        map.put("article",articleRepository.save(article));
        map.put("commentId",replyDto.getCommentId());
        map.put("replyId",replyId);
        return map;
    }

    @Override
    public void removeArticle(String articleId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = articleTemRepository.getArticleById(articleId);
        if(article.getWriter().getEmail().equals(email)){
            article.setDeleted(true);
            articleRepository.save(article);
        }else{
            throw new Exception("삭제 권한이 없습니다!");
        }
    }

    @Override
    public Article removeComment(String articleId, int commentId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = articleTemRepository.getArticleById(articleId);
        for(CommentDto c : article.getComments()){
            if(c.getId()==commentId ){
                if(c.getFrom().getEmail().equals(email)){
                    c.setDeleted(true);
                    break;
                }
            }
        }
        return articleRepository.save(article);
    }

    @Override
    public Article removeReply(String articleId, int commentId, int replyId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = articleTemRepository.getArticleById(articleId);
        List<CommentDto> comments = article.getComments();
        outerLoop:
        for(CommentDto c : comments){ //댓글찾기
            if(c.getId()==commentId){
                List<ReplyDto> replies = c.getReplies();
                for(ReplyDto r: replies){ //답글찾기
                    if(r.getId()==replyId){
                        if(r.getFrom().getEmail().equals(email)){ //작성자찾기
                            replies.remove(r);
                            c.setReplies(replies);
                            break outerLoop;
                        }
                    }
                }
            }
        }
        article.setComments(comments);
        return articleRepository.save(article);
    }
}
