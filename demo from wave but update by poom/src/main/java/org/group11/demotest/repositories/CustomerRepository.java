package org.group11.demotest.repositories;

import org.group11.demotest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByIdBetween(Integer min, Integer max);
    @Query("select c from Customer c where c.customerName like %?1% or c.city like %?1% or c.country like %?1%")
    List<Customer> findContentContain(String content);
    @Query("select c from Customer c where (c.customerName like %?1% or c.city like %?1% or c.country like %?1%)" +
            "and c.id >= ?2 and c.id <= ?3")
    List<Customer> findMix(String content,Integer min, Integer max);
}
