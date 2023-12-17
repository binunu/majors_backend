package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.CommentDto;
import com.binunu.majors.contents.dto.Major;
import com.binunu.majors.contents.dto.ReplyDto;
import com.binunu.majors.contents.repository.ArticleRepository;
import com.binunu.majors.contents.repository.ArticleTemRepository;
import com.binunu.majors.contents.repository.MajorRepository;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServeBoardServiceImpl implements ServeBoardService{
    private final MajorRepository majorRepository;
    private final ArticleRepository articleRepository;
    private final MainBoardService mainBoardService;
    private final ArticleTemRepository articleTemRepository;


    @Override
    public List<Major> getDistinctLargeMajor() throws Exception {
        return majorRepository.findDistinctLargeMajor();
    }

    @Override
    public List<Major> getDistinctMiddleMajor(String large) throws Exception {
        return majorRepository.findDistinctMiddleMajor(large);
    }

    @Override
    public List<Major> getDistinctMiddleMajor() throws Exception {
        return majorRepository.findDistinctMiddleMajor();
    }

    public List<Major> getDistinctSmallMajor(String middle) throws Exception {
        return majorRepository.findDistinctSmallMajor(middle);
    }

    @Override
    public Article bookmark(String articleId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = mainBoardService.getArticleDetail(articleId);
        List<String> list = article.getScraps();
        if(list.contains(email)){
             list.remove(email);
        }else{
            list.add(email);
        }
        article.setScraps(list);
        return articleRepository.save(article);
    }

    @Override
    public Article sympathy(String articleId, int commentId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = mainBoardService.getArticleDetail(articleId); // 게시글찾기
        List<String> sympathyList = null;
        //댓글의 공감리스트 찾기
        for(CommentDto comment :article.getComments()){
            if(comment.getId()==commentId) {
                sympathyList = comment.getSympathy();
                break;
            }
        }
        //공감처리
        if(sympathyList.contains(email)){
            sympathyList.remove(email);
        }else{
            sympathyList.add(email);
        }
        //공감리스트를 갱신하고 댓글을 만들어서 article에 저장
        List<CommentDto> updateComments = article.getComments();
        for(CommentDto c : updateComments){
            if(c.getId()==commentId){
                c.setSympathy(sympathyList); //공감 저장
                break;
            }
        }
        article.setComments(updateComments);
        return articleRepository.save(article);
    }

    @Override
    public Article sympathy(String articleId, int commentId, int replyId) throws Exception {
        String email = JwtUtil.getCurrentMemberEmail();
        Article article = mainBoardService.getArticleDetail(articleId); // 게시글찾기

        //답글의 공감리스트 찾기
        List<CommentDto> updateComments = article.getComments();

        for(CommentDto comment :article.getComments()){ //댓글찾기
            if(comment.getId()==commentId){
                for(ReplyDto reply : comment.getReplies()){
                    if(reply.getId()==replyId){
                        //공감 업데이트
                        List<String> sympathyList = reply.getSympathy();
                        if(sympathyList.contains(email)){
                            sympathyList.remove(email);
                        }else{
                            sympathyList.add(email);
                        }
                        reply.setSympathy(sympathyList);
                        break;
                    }
                }
            }
        }

        article.setComments(updateComments);
        return articleRepository.save(article);
    }

    @Override
    public Map<String, Object> reaction(String articleId, String reactionType) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        String email = JwtUtil.getCurrentMemberEmail();
        String state= articleTemRepository.updateArticleReaction(articleId,email,reactionType);
        Article article = articleTemRepository.getArticleById(articleId);
        map.put("goods",article.getGoods());
        map.put("bads",article.getBads());
        map.put("state",state);
        return map;
    }
}
