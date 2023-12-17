package com.binunu.majors.membership.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Document(collection = "member")
public class Member {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,16}$", message = "8~16자이내, 영문자와 숫자를 1개 이상 포함해주세요")
    private String password;
    @Indexed(unique = true)
    private String nickname;
    private String major;
    private String middleMajor;
    private String graduate;
    private String profile;
    private List<String> articles;
    private List<String> scraps;
    private List<String> goods;
    private List<String> bads;
    private List<String> comments;
    private String notifications;
    private List<String> roles;
    @CreatedDate
    private Date joinedAt;

}