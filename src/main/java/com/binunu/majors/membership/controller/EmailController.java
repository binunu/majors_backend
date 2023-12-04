package com.binunu.majors.membership.controller;

import com.binunu.majors.membership.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("member")
public class EmailController {
    private final EmailService emailService;
    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }
    @PostMapping("/email/send")
    public ResponseEntity<String> sendAuthNumByEmail(@RequestBody Map<String,String> body){
        String email = body.get("email");

        try{
            String code=emailService.sendAuthNumByEmail(email);
            return new ResponseEntity<String>(code, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        }
    }
}
