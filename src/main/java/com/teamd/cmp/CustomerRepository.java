package com.teamd.cmp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @Query("SELECT a FROM Customer a WHERE a.contact=:contact")
    List<Customer> fetchCustomerByContact(@Param("contact") String contact);
}
