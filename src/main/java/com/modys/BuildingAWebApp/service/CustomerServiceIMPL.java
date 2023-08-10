package com.modys.BuildingAWebApp.service;

import com.modys.BuildingAWebApp.controller.CustomerController;
import com.modys.BuildingAWebApp.model.Customer;
import com.modys.BuildingAWebApp.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.modys.BuildingAWebApp.encryptor.Encryptor.*;

@AllArgsConstructor
@Service
public class CustomerServiceIMPL implements CustomerService {

    static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {

        Customer dbCustomer = customerRepository.findByEmail(customer.getEmail());

        if (dbCustomer != null) throw new RuntimeException("Email Already Used");

        customer.setPassword(encrypt(customer.getPassword()));

        customerRepository.save(customer);
        logger.info(customer.getEmail() + " REGISTERED");
    }

    @Override
    public void findCustomer(Customer customer) {

        Customer dbCustomer = customerRepository.findByEmail(customer.getEmail());

        if (dbCustomer.getEmail() == null) throw new RuntimeException("Email Does Not Exist");

        if (decrypt(customer.getPassword(), dbCustomer.getPassword())) {
            logger.info(dbCustomer.getEmail() + " LOGGED IN");
        } else {
            throw new RuntimeException("Invalid Credentials");
        }

    }

    public void updateCustomerResetPasswordToken(String token, String email) throws RuntimeException {

        Customer customer = customerRepository.findByEmail(email);

        if (customer != null) {
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer Not Found");
        }
    }

    public Customer getToken(String resetPasswordToken) {
        return customerRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Customer customer, String newPassword) {

        String encodedPassword = encrypt(newPassword);

        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);

        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerDetails(Customer customer) {

        Customer dbCustomer = customerRepository.findByEmail(customer.getEmail());

        dbCustomer.setFirstName(customer.getFirstName());
        dbCustomer.setLastName(customer.getLastName());
        dbCustomer.setPhoneNumber(customer.getPhoneNumber());

        customerRepository.save(dbCustomer);

    }

    public Customer getAllCustomerDetails(String customerEmail) {

        return customerRepository.customerDetails(customerEmail);
    }


}
