package com.binunu.majors.contents.repository;

import com.binunu.majors.contents.dto.MajorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.Document;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MajorRepository{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MajorRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }


    public List<MajorDto> findDistinctLargeMajor(){
        AggregationOperation groupOperation = Aggregation.group("large").first("large").as("large");
        AggregationOperation sortOperation = Aggregation.sort(Sort.by("middle").ascending());
        Aggregation aggregation = Aggregation.newAggregation(groupOperation,sortOperation);
        AggregationResults<MajorDto> result = mongoTemplate.aggregate(aggregation,"major", MajorDto.class);
        return result.getMappedResults();
    }
    public List<MajorDto> findDistinctMiddleMajor(String large){
        AggregationOperation matchOperation = Aggregation.match(Criteria.where("large").is(large));
        AggregationOperation groupOperation = Aggregation.group("middle")
                .first("middle").as("middle")
                .first("large").as("large");
        AggregationOperation sortOperation = Aggregation.sort(Sort.by("middle").ascending());
        Aggregation aggregation = Aggregation.newAggregation(groupOperation,matchOperation,sortOperation);
        AggregationResults<MajorDto> result = mongoTemplate.aggregate(aggregation,"major", MajorDto.class);
        return result.getMappedResults();
    }

    public List<MajorDto> findDistinctMiddleMajor(){
        AggregationOperation groupOperation = Aggregation.group("middle")
                .first("middle").as("middle");
        AggregationOperation sortOperation = Aggregation.sort(Sort.by("middle").ascending());
        Aggregation aggregation = Aggregation.newAggregation(groupOperation,sortOperation);
        AggregationResults<MajorDto> result = mongoTemplate.aggregate(aggregation,"major", MajorDto.class);
        return result.getMappedResults();
    }

    public List<MajorDto> findDistinctSmallMajor(String middle) {
        AggregationOperation matchOperation = Aggregation.match(Criteria.where("middle").is(middle));
        AggregationOperation sortOperation = Aggregation.sort(Sort.by("small").ascending());
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,sortOperation);
        AggregationResults<MajorDto> result = mongoTemplate.aggregate(aggregation, "major", MajorDto.class);
        return result.getMappedResults();
    }
}
