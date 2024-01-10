package com.binunu.majors.contents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private String dir = "C://Users//홍성빈//Desktop//majors//upload//";

    @Override
    public String fileUpload(MultipartFile file) {
        try{
            if(file != null&&!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String uniqueName = System.currentTimeMillis() + fileName;

                File newFile = new File(dir + uniqueName);
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                file.transferTo(newFile);
                return uniqueName;
            }
            return null;
        }catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] fileView(String fileName) {
        try{
            File file = new File(dir+fileName);
            return Files.readAllBytes(file.toPath());
        }catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
    }
}
