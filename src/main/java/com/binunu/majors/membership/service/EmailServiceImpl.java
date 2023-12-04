package com.binunu.majors.membership.service;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    public EmailServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    
    @Value("${spring.mail.host}")
    private String host;

    //인증번호 생성로직
    @Override
    public String generateVerificationCode() throws Exception {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++){
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Override
        public String sendAuthNumByEmail(String email) throws Exception {
            String code = generateVerificationCode();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

            helper.setTo(email);
            helper.setFrom(host,"전공자들");
            helper.setSubject("[전공자들] 회원가입 인증 번호 안내");
            String htmlText = "<h2>전공자들 인증 번호 안내</h2>"+
                    "<br/>"+
                    "<p>회원님께서 요청하신 전공자들 회원 가입을 위한 이메일 인증 번호를 알려드립니다.</p>"+
                    "<br/>"+
                    "<p>아래 인증코드를 복사하여 입력해주시기 바랍니다.</p>"+
                    "<br/>"+
                    "<h3>인증코드 : "+code+"</h3>"+
                    "<br/>";
            helper.setText(htmlText, true);
            javaMailSender.send(message);
            return code;
        }
}
