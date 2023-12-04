package com.binunu.majors.contents.service;

import com.binunu.majors.contents.dto.MajorDto;
import com.binunu.majors.contents.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServeBoardServiceImpl implements ServeBoardService{
    private final MajorRepository majorRepository;
    @Autowired
    public ServeBoardServiceImpl(MajorRepository majorRepository){
        this.majorRepository = majorRepository;
    }

    @Override
    public List<MajorDto> getDistinctLargeMajor() throws Exception {
        return majorRepository.findDistinctLargeMajor();
    }

    @Override
    public List<MajorDto> getDistinctMiddleMajor(String large) throws Exception {
        return majorRepository.findDistinctMiddleMajor(large);
    }

    @Override
    public List<MajorDto> getDistinctMiddleMajor() throws Exception {
        return majorRepository.findDistinctMiddleMajor();
    }

    public List<MajorDto> getDistinctSmallMajor(String middle) throws Exception {
        return majorRepository.findDistinctSmallMajor(middle);
    }
}
