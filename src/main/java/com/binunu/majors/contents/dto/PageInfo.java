package com.binunu.majors.contents.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int allPage;
    private int curPage;
    private int startPage;
    private int endPage;
}
