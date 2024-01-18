package com.binunu.majors.contents.repository;


import com.binunu.majors.contents.dto.Article;
import com.binunu.majors.contents.dto.Reaction;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleTemRepository {
    private final MongoTemplate mongoTemplate;
    public String updateArticleReaction(String articleId,String email, String reactionType){
        Map<String,Object> map = null;
        Query query = new Query(Criteria.where("id").is(articleId).and("reactions.email").is(email));
        Article article = mongoTemplate.findOne(query, Article.class);
        //유저가 없으면 추가
        if(article == null) {
            Update update = new Update().push("reactions", new Reaction(email, reactionType));
            if(reactionType.equals("T")){
                update.inc("goods",1);
            }else{
                update.inc("bads",1);
            }
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(articleId)),update,Article.class);
            return reactionType;
        }
        //유저가 있으면 업데이트
        else{
            Optional<String> userReaction = article.getReactions().stream()
                    .filter(reaction -> reaction.getEmail().equals(email))
                    .map(Reaction::getState)
                    .findFirst();
            //현재상태와 유저가 누른 리액션에 따라 개수 및 상태 업데이트하는 객체를 반환하는 메소드
            map = updateReaction(reactionType, userReaction.get());
            Update update = (Update)map.get("update");
            mongoTemplate.updateFirst(query,update,Article.class);
            return (String)map.get("state");
        }
    }

    //현재상태와 유저가누른 리액션에 따라 개수 및 상태 업데이트하는 객체를 반환하는 메소드
    private Map<String,Object> updateReaction(String now, String old){
        //현재 좋아요와 싫어요의 개수 받아오기
        Map<String,Object> map = new HashMap<>();
        Update update = null;
        String state = "";
        if(now.equals("T")&&old.equals("T")){ //좋아요 상태일 때 좋아요 누르면 N상태, 좋아요 개수 -1
            update = new Update().inc("goods",-1).set("reactions.$.state","N");
            state = "N";
        }else if(now.equals("T")&&old.equals("F")){ // 싫어요 상태일 때 좋아요 누르면 좋아요 활성화(T), 좋아요 +1, 싫어요 -1
            update = new Update().inc("goods",1).inc("bads",-1).set("reactions.$.state","T");
            state = "T";
        }else if(now.equals("T")&&old.equals("N")){ //모두 비활성화(N)일 때 좋아요 누르면 좋아요 활성화(T), 좋아요 +1
            update = new Update().inc("goods",1).set("reactions.$.state","T");
            state = "T";
        }else if(now.equals("F")&&old.equals("F")){//싫어요 상태일 때 싫어요 누르면 N상태, 싫어요 개수 -1
            update = new Update().inc("bads",-1).set("reactions.$.state","N");
            state = "N";
        }else if(now.equals("F")&&old.equals("T")){//좋아요 상태일 때 싫어요 누르면 싫어요 활성화(F), 좋아요 -1, 싫어요 +1
            update = new Update().inc("bads",1).inc("goods",-1).set("reactions.$.state","F");
            state = "F";
        }else if(now.equals("F")&&old.equals("N")){//모두 비활성화(N)일 때 싫어요 누르면 싫어요 활성화, 싫어요 +1
            update = new Update().inc("bads",1).set("reactions.$.state","F");
            state = "F";
        }

        map.put("state",state);
        map.put("update",update);
        return map;
    }


    public Article getArticleById(String articleId){
        return mongoTemplate.findById(articleId,Article.class);
    }

}
