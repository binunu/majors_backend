package com.binunu.majors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class MajorsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajorsBackendApplication.class, args);
        System.out.print("git init");
    }
}
