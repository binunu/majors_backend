package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.*;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleTemRepository;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberInfoDto;
import com.binunu.majors.membership.service.MemberService;
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
        MemberInfoDto memberProfileDto = new MemberInfoDto(mem);
        article.setComments(new ArrayList<CommentDto>());
        article.setWriter(memberProfileDto);
        article.setGoods(0);
        article.setBads(0);
        article.setReactions(new ArrayList<Reaction>());
        article.setScraps(new ArrayList<String>());

        return articleRepository.save(article);
    }

    @Override
//    public List<Article> getArticleList() throws Exception {
    public Map<String,Object> getArticleListByType(String boardType, int page, int cnt) throws Exception {
        Map<String,Object> res = new HashMap<String,Object>();
        PageRequest pageRequest = PageRequest.of(page-1,cnt, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository.findByBoardType(pageRequest, boardType);
        PageInfo pageInfo = new PageInfo();
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
        PageRequest pageRequest = PageRequest.of(page-1,cnt, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository.findByBoardTypeAndMiddleMajor(pageRequest, boardType, middleMajor);

        PageInfo pageInfo = new PageInfo();
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
    public Article createComment(CommentDto commentDto) throws Exception {
        commentDto.setReplies(new ArrayList<ReplyDto>()); //테스트필요

        Member member = memberService.getCurrentMember();
        MemberInfoDto memberProfileDto = new MemberInfoDto(member);
        commentDto.setFrom(memberProfileDto); //작성자 기록
        //등록일 설정
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        commentDto.setCreatedAt(strDate);
        //공감
        commentDto.setSympathy(new ArrayList<String>());
        //id
        commentDto.setId(CommentDto.getNum());
        CommentDto.numbering();

        //article update
        Article article = getArticleDetail(commentDto.getTo()); //to없애기
        List<CommentDto> comment = article.getComments();
        comment.add(commentDto);
        article.setComments(comment);

        return articleRepository.save(article);
    }

    @Override
    public Article createReply(ReplyDto replyDto) throws Exception {
        //등록일
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        replyDto.setCreatedAt(strDate);
        //공감수초기화
        replyDto.setSympathy(new ArrayList<String>());
        //답글작성자
        Member member = memberService.getCurrentMember();
        MemberInfoDto memberProfileDto = new MemberInfoDto(member);
        replyDto.setFrom(memberProfileDto);
        //id넘버링
        replyDto.setId(ReplyDto.getNum());
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
        return articleRepository.save(article);
    }




}
