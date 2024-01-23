package com.binunu.majors.contents.controller;

import com.binunu.majors.contents.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;


    @GetMapping("/image/view/{filename}")
    @ResponseBody
    public byte[] fileView(@PathVariable("filename") String fileName){
        return fileService.fileView(fileName);
    }
}
