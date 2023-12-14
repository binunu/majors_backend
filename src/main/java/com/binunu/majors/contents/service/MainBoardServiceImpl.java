package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.ArticleDto;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.dto.MemberProfileDto;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.membership.service.MemberService;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainBoardServiceImpl implements MainBoardService {
    private final ArticleRepository articleRepository;
    private final MemberService memberService;
    @Override
    public ArticleDto createArticle(ArticleDto articleDto) throws Exception {
        MemberDto mem = memberService.getCurrentMember();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());

        articleDto.setWriter(mem.getEmail());
        articleDto.setUploadDate(strDate);
        articleDto.setGoods(new ArrayList<>());
        articleDto.setBads(new ArrayList<>());

        return articleRepository.save(articleDto);
    }

    @Override
    public Map<String, Object> getArticleDetail(String id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        Optional<ArticleDto> oArticle = articleRepository.findById(id);
        ArticleDto article = oArticle.orElse(null);

        MemberDto member = memberService.getMemberByEmail(article.getWriter());
        MemberProfileDto profile = new MemberProfileDto(member);

        map.put("profile",profile);
        map.put("article",article);
        return map;
    }

    @Override
    public void createComment(CommentDto commentDto) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        commentDto.setFrom(email);

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = format.format(new Date());
        commentDto.setUploadDate(strDate);

        commentDto.setReplies(new ArrayList<CommentDto>());

    }

}
