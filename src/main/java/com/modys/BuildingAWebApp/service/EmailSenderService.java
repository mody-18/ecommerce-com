package com.modys.BuildingAWebApp.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {

    public void sendResetPasswordLinkViaEmail(String email, String resetPasswordLink) throws MessagingException;
}
