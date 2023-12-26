package com.binunu.majors.membership.service;

import com.binunu.majors.contents.dto.*;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleTemRepository;
import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public  class MemberActionServiceImpl implements MemberActionService{
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ArticleTemRepository articleTemRepository;
    private final ModelMapper modelMapper;
    //정보저장하기
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
    public void writeComment(String articleId, int commentId) throws Exception {
        Member member = memberService.getCurrentMember();
        List<CommentInfo> comments = member.getComments();
        //각 id만 저장
        CommentInfo comment = new CommentInfo(articleId, commentId);
        comments.add(comment);
        member.setComments(comments);
        memberRepository.save(member);
    }

    @Override
    public void writeReply(String articleId, int commentId, int replyId) throws Exception {
        Member member = memberService.getCurrentMember();
        List<CommentInfo> replies = member.getComments();
        CommentInfo reply = new CommentInfo(articleId, commentId, replyId);
        replies.add(reply);
        member.setComments(replies);
        memberRepository.save(member);
    }
    @Override
    public void reaction(String articleId, String state) throws Exception {
        Member member = memberService.getCurrentMember();
        log.info(member.toString());
        List<String> goods = member.getGoods();
        List<String> bads = member.getBads();
        switch (state){
            case "T"->{
                goods.add(articleId);
                bads.remove(articleId);
            }
            case "F"->{
                goods.remove(articleId);
                bads.add(articleId);
            }
            case "N"->{
                goods.remove(articleId);
                bads.remove(articleId);
            }
        }
        member.setGoods(goods);
        member.setBads(bads);
        memberRepository.save(member);

    }

    //정보 가져오기
    @Override
    public Map<String, Object> getLogArticle(int page,int cnt) throws Exception {
        Map<String, Object> res = new HashMap<>();
        List<String> articles = memberService.getCurrentMember().getArticles();
        List<ArticleInfo> list = new ArrayList<>();
        int startIndex = (page-1)*cnt;
        int endIndex = Math.min(articles.size(),startIndex+cnt);
        List<String> subArticles = articles.subList(startIndex,endIndex);

        for(String aId : subArticles){
            Article article = articleTemRepository.getArticleById(aId);
            ArticleInfo articleInfo = modelMapper.map(article,ArticleInfo.class);
            articleInfo.setCommentCnt(article.getComments().size());
            list.add(articleInfo);
        }
        PageInfo pageInfo = pagination(page, cnt ,articles.size());
        res.put("list",list);
        res.put("pageInfo",pageInfo);
        return res;
    }

    @Override
    public Map<String, Object> getLogScrap(int page,int cnt) throws Exception {
        Map<String, Object> res = new HashMap<>();
        List<String> articles = memberService.getCurrentMember().getScraps();
        List<ArticleInfo> list = new ArrayList<>();
        int startIndex = (page-1)*cnt;
        int endIndex = Math.min(articles.size(),startIndex+cnt);
        List<String> subArticles = articles.subList(startIndex,endIndex);

        for(String aId : subArticles){
            Article article = articleTemRepository.getArticleById(aId);
            ArticleInfo articleInfo = modelMapper.map(article,ArticleInfo.class);
            articleInfo.setCommentCnt(article.getComments().size());
            list.add(articleInfo);
        }
        PageInfo pageInfo = pagination(page, cnt ,articles.size());
        res.put("list",list);
        res.put("pageInfo",pageInfo);
        return res;
    }

    @Override
    public Map<String, Object> getLogGoods(int page,int cnt) throws Exception {
        Map<String, Object> res = new HashMap<>();
        List<String> articles = memberService.getCurrentMember().getGoods();
        List<ArticleInfo> list = new ArrayList<>();
        int startIndex = (page-1)*cnt;
        int endIndex = Math.min(articles.size(),startIndex+cnt);
        List<String> subArticles = articles.subList(startIndex,endIndex);

        for(String aId : subArticles){
            Article article = articleTemRepository.getArticleById(aId);
            ArticleInfo articleInfo = modelMapper.map(article,ArticleInfo.class);
            articleInfo.setCommentCnt(article.getComments().size());
            list.add(articleInfo);
        }
        PageInfo pageInfo = pagination(page, cnt ,articles.size());
        res.put("list",list);
        res.put("pageInfo",pageInfo);
        return res;
    }

    @Override
    public Map<String, Object> getLogBads(int page,int cnt) throws Exception {
        Map<String, Object> res = new HashMap<>();
        List<String> articles = memberService.getCurrentMember().getBads();
        List<ArticleInfo> list = new ArrayList<>();
        int startIndex = (page-1)*cnt;
        int endIndex = Math.min(articles.size(),startIndex+cnt);
        List<String> subArticles = articles.subList(startIndex,endIndex);

        for(String aId : subArticles){
            Article article = articleTemRepository.getArticleById(aId);
            ArticleInfo articleInfo = modelMapper.map(article,ArticleInfo.class);
            articleInfo.setCommentCnt(article.getComments().size());
            list.add(articleInfo);
        }
        PageInfo pageInfo = pagination(page, cnt ,articles.size());
        res.put("list",list);
        res.put("pageInfo",pageInfo);
        return res;
    }

    @Override
    public Map<String, Object> getLogComments(int page, int cnt) throws Exception {
        Map<String, Object> res = new HashMap<>();
        //삭제되지않은 댓글만 가져오기
        List<CommentInfo> comments = memberService.getCurrentMember().getComments().stream()
                .filter(comment -> !comment.isDeleted())
                .collect(Collectors.toList());

        Collections.reverse(comments);
        int startIndex = (page-1)*cnt;
        int endIndex = Math.min(comments.size(),startIndex+cnt);
        List<CommentInfo> subComments = comments.subList(startIndex,endIndex);

        for(CommentInfo ci : subComments){
            Article article = articleTemRepository.getArticleById(ci.getArticleId());
            ci.setArticleBoardType(article.getBoardType());
            ci.setArticleTitle(article.getTitle());
            
            CommentDto c = article.getComments().stream()
                    .filter(comment ->comment.getId()==ci.getCommentId())
                    .findFirst().orElse(null);
            if(ci.getType().equals("comment")){ //댓글
                ci.setSympathyCnt(c.getSympathy().size());
                ci.setContent(c.getContent());
                ci.setReplyCnt(c.getReplies().size());
            }else{ //답글
                ReplyDto r = c.getReplies().stream()
                        .filter(reply -> reply.getId()==ci.getReplyId())
                        .findFirst().orElse(null);
                ci.setSympathyCnt(r.getSympathy().size());
                ci.setContent(r.getContent());
            }
        }
        PageInfo pageInfo = pagination(page, cnt ,comments.size());
        res.put("list",subComments);
        res.put("pageInfo",pageInfo);
        return res;
    }

    @Override
    public void removeArticle(String articleId) throws Exception {
        Member member = memberService.getCurrentMember();
        member.getArticles().remove(articleId);
        memberRepository.save(member);
    }

    @Override
    public void removeComment(String articleId, int commentId) throws Exception {
        Member member = memberService.getCurrentMember();
        for(CommentInfo c : member.getComments()){
            if(c.getArticleId().equals(articleId) && c.getCommentId()==commentId ){
                c.setDeleted(true);
                break;
            }
        }
        memberRepository.save(member);
    }

    @Override
    public void removeReply(String articleId, int commentId, int replyId) throws Exception {
        Member member = memberService.getCurrentMember();
        log.info("리플삭제들어왔나?2");
        member.getComments().removeIf(reply->
                reply.getType().equals("reply") && reply.getArticleId().equals(articleId) && reply.getCommentId()==commentId && reply.getReplyId()==replyId);
        memberRepository.save(member);
    }

    public PageInfo pagination(int curPage, int cnt, int size) throws Exception{
        int range = 5; //페이지네이션범위. 1~5/6~10/11~15 씩 출력
        int allPage = (int)Math.ceil((double) size/cnt);
        int startPage = (curPage-1)/range*range+1;
        int endPage =  Math.min(startPage+range-1,allPage);

        return new PageInfo(size,allPage,curPage,startPage,endPage);
    }
}
