package com.modys.BuildingAWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Where the customer will start.
public class MainController {

    @GetMapping("/")
    public String registrationPage() {
        return "AuthenticationPage";
    }

    @GetMapping("/authenticationPage")
    public String authenticationPage() {
        return "AuthenticationPage";
    }

}
