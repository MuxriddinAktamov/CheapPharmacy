package com.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServise {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String ToAccount, String title, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(ToAccount);
        mailMessage.setSubject(title);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);
    }
}
