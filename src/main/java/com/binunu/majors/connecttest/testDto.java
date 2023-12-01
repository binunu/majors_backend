package com.binunu.majors.connecttest;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document(collection = "user")
public class testDto {

    private String name;
    private int age;
    private String job;
    private String music;
}
