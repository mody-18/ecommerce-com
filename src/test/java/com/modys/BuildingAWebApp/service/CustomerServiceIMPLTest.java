package com.modys.BuildingAWebApp.service;

import com.modys.BuildingAWebApp.model.Customer;
import com.modys.BuildingAWebApp.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static com.modys.BuildingAWebApp.encryptor.Encryptor.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceIMPLTest {

    @Mock private CustomerRepository customerRepository;
    private CustomerServiceIMPL customerServiceIMPLTest;

    @BeforeEach
    void setUp() {
        customerServiceIMPLTest = new CustomerServiceIMPL(customerRepository);
    }

    @Test
    void canCreateValidCustomerForRegistering() {
        // Given
        Customer customer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                "password123"
        );
        // When
        customerServiceIMPLTest.createCustomer(customer);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class); // Customer object is sent in

        verify(customerRepository).save(customerArgumentCaptor.capture()); // verifys whether .save was run

        Customer captured = customerArgumentCaptor.getValue(); // Grabs customer object that was sent in

        assertThat(captured).isEqualTo(customer); // verifys whether the correct customer object was sent
    }

    @Test
    void canDetectInvalidCustomerForRegistering() {
        // Given
        Customer customer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                "password123"
        );
        // When
        given(customerRepository.emailExists(customer.getEmail()))
                .willReturn(true); // Sets email exists to true
        // Then
        assertThatThrownBy(() -> customerServiceIMPLTest.createCustomer(customer))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email Already Used"); // Checks whether error is throw

        verify(customerRepository, never()).save(customer); // Checks whether .save() was never run

    }

    @Test
    void canDetectNonRegisteredCustomerForLogin() {
        // Given
        Customer customer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                "password"
        );

        // When
        given(customerRepository.emailExists(customer.getEmail()))
                .willReturn(false);

        // Then
        assertThatThrownBy(() -> customerServiceIMPLTest.findCustomer(customer))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email Does Not Exist");

    }

    @Test
    void canFindCustomerForLogin() {
        // Given
        Customer customer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                "password"
        );

        Customer currentCustomer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                encrypt("password")
        );

        // When
        given(customerRepository.emailExists(customer.getEmail()))
                .willReturn(true);

        given(customerRepository.customerDetails(customer.getEmail())).willReturn(currentCustomer);

        // Then
        assertThat(customerServiceIMPLTest.findCustomer(customer)).isEqualTo(customer.getEmail());

    }

    @Test
    void canThrowErrorForInvalidLoginCredentials() {
        // Given
        Customer customer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                "password"
        );

        Customer currentCustomer = new Customer(
                "Mike.Smith@gmail.com",
                "Mike",
                "Smith",
                encrypt("password123")
        );

        // When
        given(customerRepository.emailExists(customer.getEmail()))
                .willReturn(true);

        given(customerRepository.customerDetails(customer.getEmail())).willReturn(currentCustomer);

        // Then
        assertThatThrownBy(() -> customerServiceIMPLTest.findCustomer(customer))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid Credentials");


    }

}