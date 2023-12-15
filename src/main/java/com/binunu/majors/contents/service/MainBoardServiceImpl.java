package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.ArticleDto;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleRepositoryTemp;
import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.dto.MemberProfileDto;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.membership.service.MemberService;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.xml.stream.events.Comment;
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
    public ArticleDto createArticle(ArticleDto articleDto) throws Exception {
        MemberDto mem = memberService.getCurrentMember();
        MemberProfileDto memberProfileDto = new MemberProfileDto(mem);
        articleDto.setComments(new ArrayList<CommentDto>());
        articleDto.setWriter(memberProfileDto);
        articleDto.setGoods(new ArrayList<>());
        articleDto.setBads(new ArrayList<>());

        return articleRepository.save(articleDto);
    }

    @Override
    public ArticleDto getArticleDetail(String id) throws Exception {
        return articleRepositoryTemp.getArticleById(id);
    }

    @Override
    public ArticleDto createComment(CommentDto commentDto) throws Exception {
        commentDto.setReplies(new ArrayList<ReplyDto>()); //테스트필요

        MemberDto memberDto = memberService.getCurrentMember();
        MemberProfileDto memberProfileDto = new MemberProfileDto(memberDto);
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
        ArticleDto articleDto = getArticleDetail(commentDto.getTo()); //to없애기
        List<CommentDto> comment = articleDto.getComments();
        if(comment == null){
            comment = new ArrayList<CommentDto>(); //이거해야하나?
        }
        comment.add(commentDto);
        articleDto.setComments(comment);

        return articleRepository.save(articleDto);
    }

    @Override
    public ArticleDto createReply(ReplyDto replyDto) throws Exception {
        //등록일
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        replyDto.setCreatedAt(strDate);
        //공감수초기화
        replyDto.setSympathy(new ArrayList<String>());
        //답글작성자
        MemberDto memberDto = memberService.getCurrentMember();
        MemberProfileDto memberProfileDto = new MemberProfileDto(memberDto);
        replyDto.setFrom(memberProfileDto);
        //id넘버링
        replyDto.setId(ReplyDto.getNum());
        ReplyDto.numbering();
        //article update
        ArticleDto articleDto = getArticleDetail(replyDto.getArticleId());
        for(CommentDto c : articleDto.getComments()){
            if(c.getId()==replyDto.getReplyId()){
                List<ReplyDto> list = c.getReplies();
                System.out.println(list.size());
                list.add(replyDto);
                c.setReplies(list);
                break;
            }
        }
        return articleRepository.save(articleDto);
    }


}
