package com.modys.BuildingAWebApp.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceIMPL implements EmailSenderService{

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordLinkViaEmail(String email, String resetPasswordLink) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("www.BasicAuthentication.com");
        helper.setTo(email);
        helper.setSubject("Reset Password");

        String content = "<p>Hello,</p>" +
                "<p>You have requested to reset your password.</p>" +
                "<p>Click the link below to change your password:</p>" +
                "<p><b><a href=\"" + resetPasswordLink + "\">Change my password<a></b></p>" +
                "<p>Ignore this email if you do remember your password, or you have not made the request</p>";

        helper.setText(content, true);
        mailSender.send(message);
    }
}
