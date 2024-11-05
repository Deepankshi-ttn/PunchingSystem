package com.example.punchingSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendNotification() {
        int x = 5 / 0;
        System.out.println("Async Method called");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("deepankshi.arora@tothenew.com");
        mail.setSubject("Defaulters List:");
        mail.setText("Dummy Data");  // TODO add defaulter information
        mailSender.send(mail);
    }
}
