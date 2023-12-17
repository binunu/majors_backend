package com.binunu.majors.contents.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    private String email;
    private String state; //"T", "F", "N"
}
