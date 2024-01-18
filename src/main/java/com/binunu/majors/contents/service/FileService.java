package com.binunu.majors.contents.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface FileService {
    String fileUpload(MultipartFile file);
    byte[] fileView(String fileName);
}
