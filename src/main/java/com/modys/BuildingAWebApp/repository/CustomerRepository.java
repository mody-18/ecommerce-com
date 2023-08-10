package com.modys.BuildingAWebApp.repository;

import com.modys.BuildingAWebApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);

//    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN " +
//            "TRUE ELSE FALSE END " +
//            "FROM Customer c " +
//            "WHERE c.email = ?1"
//    )
//    Boolean emailExists(String email);

    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Customer customerDetails(String email);

    public Customer findByResetPasswordToken(String token);


}
