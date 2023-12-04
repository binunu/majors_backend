package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.MajorDto;

import java.util.List;

public interface ServeBoardService {
    List<MajorDto> getDistinctLargeMajor() throws Exception;
    List<MajorDto> getDistinctMiddleMajor(String large) throws Exception;
    List<MajorDto> getDistinctMiddleMajor() throws Exception;
    List<MajorDto> getDistinctSmallMajor(String middle) throws Exception;
}
