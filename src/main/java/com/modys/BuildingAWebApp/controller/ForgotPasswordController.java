package com.modys.BuildingAWebApp.controller;

import com.modys.BuildingAWebApp.model.Customer;
import com.modys.BuildingAWebApp.service.CustomerService;
import com.modys.BuildingAWebApp.service.EmailSenderService;
import com.modys.BuildingAWebApp.utility.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import net.bytebuddy.utility.RandomString;


@Controller
public class ForgotPasswordController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("pageTitle", "Forgot Password");
        return "SendEmailVerificationPage";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {

        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            customerService.updateCustomerResetPasswordToken(token, email);
            String resetPasswordLinkURL = Utility.getSiteURL(request) + "/resetPassword?token=" + token;
            emailSenderService.sendResetPasswordLinkViaEmail(email, resetPasswordLinkURL);

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }


        return "AuthenticationPage";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@Param(value =" token") String token, Model model) {

        Customer customer = customerService.getToken(token);

        if (customer == null) {
            model.addAttribute("title", "Invalid Token");
            model.addAttribute("message", "The Token Is Invalid");
            return "StatusMessagePage";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your password");
        return "ResetPasswordForm";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(HttpServletRequest request, Model model) {

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        Customer customer = customerService.getToken(token);

        if (customer == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "StatusMessagePage";
        }

        customerService.updatePassword(customer, password);
        model.addAttribute("message", "Password has been changed");

        return "StatusMessagePage";
    }


}
