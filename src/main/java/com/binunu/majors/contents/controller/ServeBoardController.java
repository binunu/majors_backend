package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.dto.MajorDto;
import com.binunu.majors.contents.service.ServeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Slf4j
@RestController
@RequestMapping("contents")
public class ServeBoardController {

    private final ServeBoardService serveBoardService;

    @Autowired
    public ServeBoardController(ServeBoardService serveBoardService) {
        this.serveBoardService = serveBoardService;
    }

    @GetMapping("getMajorList/large")
    public ResponseEntity<List<MajorDto>> getDistinctLargeMajor() {
        try {
            List<MajorDto> list = serveBoardService.getDistinctLargeMajor();
            return new ResponseEntity<List<MajorDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<MajorDto>>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("getMajorList/middle")
    public ResponseEntity<List<MajorDto>> getDistinctMiddleMajor(@RequestParam(value = "large", required = false) String large) {
            List<MajorDto> list = null;
        try {
            if(large!=null){
                list = serveBoardService.getDistinctMiddleMajor(large);
            }else{
                list = serveBoardService.getDistinctMiddleMajor();
            }
            for(MajorDto m:list){
            System.out.println(m.toString());
            }
            return new ResponseEntity<List<MajorDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<MajorDto>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getMajorList/small")
    public ResponseEntity<List<MajorDto>> getDistinctSmallMajor(@RequestParam("middle") String middle) {
        try {
            List<MajorDto> list = serveBoardService.getDistinctSmallMajor(middle);
            return new ResponseEntity<List<MajorDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<MajorDto>>(HttpStatus.BAD_REQUEST);
        }
    }

}
