package org.example.pretest.repository;

import org.example.pretest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerNameContainingOrIdEqualsOrPhoneContaining(String customerName, Integer id, String phone);
    List<Customer> findByCustomerNameContainingOrPhoneContaining(String customerName, String phone);

    @Query("SELECT DISTINCT c.country FROM Customer c")
    List<String> findDistinctCountry();

    @Query("SELECT DISTINCT c.city FROM Customer c WHERE c.country = ?1")
    List<String> findCitiesByCountry(String country);

    @Query("SELECT c FROM Customer c WHERE c.country = ?1 and c.city= ?2")
    List<Customer> findCustomerByCountryAndCity(String country, String city);

    @Query("SELECT c FROM Customer c WHERE c.country = ?1")
    List<Customer> findCustomerByCountry(String country);

}
