package com.binunu.majors.membership.service;

public interface EmailService {
    public String generateVerificationCode() throws Exception;
    public String sendAuthNumByEmail(String email)throws Exception;
}
