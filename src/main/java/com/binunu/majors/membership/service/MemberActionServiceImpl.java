package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberActionServiceImpl implements MemberActionService{
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Override
    public void createArticle(String articleId) throws Exception {
        Member member = memberService.getCurrentMember();
        List<String> list = member.getArticles();
        list.add(articleId);
        member.setArticles(list); //null일때도 동작하나?
        memberRepository.save(member);
    }

    @Override
    public void bookmark(String articleId) throws Exception {
        Member member = memberService.getCurrentMember();
        List<String> scrapArticles = member.getScraps(); //스크랩한 게시글 가져오기
        if(scrapArticles.contains(articleId)){ // 스크랩이 있으면 제거
            scrapArticles.remove(articleId);
        }else{    // 스크랩이 없으면 추가
            scrapArticles.add(articleId);
        }
        member.setScraps(scrapArticles);
        memberRepository.save(member);
    }

    @Override
    public void sympathy(String articleId, int commentId, int replyId) throws Exception {

    }
    @Override
    public void sympathy(String articleId, int commentId) throws Exception {

    }
}
