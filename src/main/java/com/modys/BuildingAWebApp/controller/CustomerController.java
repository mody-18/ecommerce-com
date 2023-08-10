package com.modys.BuildingAWebApp.controller;

import com.modys.BuildingAWebApp.model.Customer;
import com.modys.BuildingAWebApp.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signUp")
    public String addNewCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "RegisteredSuccessfullyPage";
    }

    @PostMapping("/mainPage")
    public String verifyCustomer(HttpServletRequest request, @ModelAttribute Customer customer, Model model) {

        customerService.findCustomer(customer);

        String email = request.getParameter("email");

        Customer dbCustomer = customerService.getAllCustomerDetails(email);

        model.addAttribute("Email", email);
        model.addAttribute("FirstName", dbCustomer.getFirstName());
        model.addAttribute("LastName", dbCustomer.getLastName());

        if (dbCustomer.getPhoneNumber() == null) {
            model.addAttribute("PhoneNumber", "Not Provided");
        } else {
            model.addAttribute("PhoneNumber", dbCustomer.getPhoneNumber());
        }
        return "MainPage";
    }

    @PostMapping("/editFirstName")
    public String editCustomerFirstName(HttpServletRequest request, Model model) {
        model.addAttribute("Email", "Email: " + request.getParameter(""));

        return "ChangeFirstNamePage";
    }

    @GetMapping("/editLastName")
    public String editCustomerLastName() {
        return "ChangeLastNamePage";
    }

    @GetMapping("/editPhoneNumber")
    public String editCustomerPhoneNumber() {
        return "ChangePhoneNumberPage";
    }

    @PostMapping("/updateFirstName")
    public String updateCustomerFirstName(HttpServletRequest request, String firstName) {

        return "MainPage";
    }

    @PostMapping("/updateLastName")
    public String updateCustomerLastName(HttpServletRequest request, String lastName) {
        return "MainPage";
    }

    @PostMapping("/updatePhoneNumber")
    public String updateCustomerPhoneNumber(HttpServletRequest request, String phoneNumber) {
        return "MainPage";
    }

}

