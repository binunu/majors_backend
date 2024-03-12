package com.binunu.majors.contents.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomUtilService {
    //년월일 시간 저장
    public static String changeDateAndTimeToString(){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return today.format(pattern);
    }
    //년월일 저장
    public static String changeDateToString(){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(pattern);
    }
}
