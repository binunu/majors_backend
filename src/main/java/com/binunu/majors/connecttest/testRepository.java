package com.binunu.majors.connecttest;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface testRepository extends MongoRepository<testDto,String> {

}

