package com.modys.BuildingAWebApp.repository;

import com.modys.BuildingAWebApp.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepositoryTest;

    @Test
    void itShouldCheckIfCustomerExistsByEmailAndReturnTrue() {
        // Given
        Customer customer = new Customer(
                "Joe.Smith@gmail.com",
                "Joe",
                "Smith",
                "securePassword"
        );
        customerRepositoryTest.save(customer);
        // When
        boolean expected = customerRepositoryTest.emailExists(customer.getEmail()); // emailExists function was removed. Tests were no longer were able to be performed
        // Then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfCustomerExistsByEmailAndNotReturnTrue() {
        // When
        boolean expected = customerRepositoryTest.emailExists("Micheal@gmail.com");
        // Then
        assertThat(expected).isFalse();
    }

    @Test
    void itShouldRetrieveCustomerDetailsSuccessfullyViaEmail() {
        // Given
        Customer customer = new Customer(
                "Joe.Smith@gmail.com",
                "Joe",
                "Smith",
                "securePassword"
        );
        // When
        customerRepositoryTest.save(customer);

        Customer customerRetrieved = customerRepositoryTest.customerDetails(customer.getEmail());
        // Then
        assertThat(customerRetrieved).isEqualTo(customer);
    }

    @Test
    void itShouldNotRetrieveCustomerDetailsSuccessfullyViaEmail() {
        // Given
        Customer customer = new Customer(
                "Joe.Smith@gmail.com",
                "Joe",
                "Smith",
                "securePassword"
        );
        // When
        Customer customerRetrieved = customerRepositoryTest.customerDetails(customer.getEmail());
        // Then
        assertThat(customerRetrieved).isNotEqualTo(customer);
    }




}