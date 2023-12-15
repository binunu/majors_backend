package com.binunu.majors.contents.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Document(collection = "major")
public class Major {
    private String large;
    private String middle;
    private String small;
    private String largeCode;
    private String middleCode;
    private String smallCode;
}
