package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleRepositoryTemp;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberProfileDto;
import com.binunu.majors.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainBoardServiceImpl implements MainBoardService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    private final ArticleRepositoryTemp articleRepositoryTemp;
    @Override
    public Article createArticle(Article article) throws Exception {
        Member mem = memberService.getCurrentMember();
        MemberProfileDto memberProfileDto = new MemberProfileDto(mem);
        article.setComments(new ArrayList<CommentDto>());
        article.setWriter(memberProfileDto);
        article.setGoods(new ArrayList<String>());
        article.setBads(new ArrayList<String>());
        article.setScraps(new ArrayList<String>());

        return articleRepository.save(article);
    }

    @Override
    public Article getArticleDetail(String id) throws Exception {
        return articleRepositoryTemp.getArticleById(id);
    }

    @Override
    public Article createComment(CommentDto commentDto) throws Exception {
        commentDto.setReplies(new ArrayList<ReplyDto>()); //테스트필요

        Member member = memberService.getCurrentMember();
        MemberProfileDto memberProfileDto = new MemberProfileDto(member);
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
        MemberProfileDto memberProfileDto = new MemberProfileDto(member);
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
