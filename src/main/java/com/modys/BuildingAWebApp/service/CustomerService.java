package com.modys.BuildingAWebApp.service;

import com.modys.BuildingAWebApp.model.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    public void createCustomer(Customer customer);

    public void findCustomer(Customer customer);

    public void updateCustomerResetPasswordToken(String token, String email);
    public Customer getToken(String resetPasswordToken);

    public void updatePassword(Customer customer, String newPassword);

    void updateCustomerDetails(Customer customer);

    public Customer getAllCustomerDetails(String customerEmail);
}
