package org.example.pretest.services;

import org.example.pretest.entities.Customer;
import org.example.pretest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> searchCustomersByContent(String search) {
        List<Customer> customers;
        try {
            Integer id = Integer.valueOf(search);
            customers = customerRepository.findByCustomerNameContainingOrIdEqualsOrPhoneContaining(search, Integer.valueOf(search), search);
        } catch(NumberFormatException e) {
            customers = customerRepository.findByCustomerNameContainingOrPhoneContaining(search, search);
        }
        return customers;
    }

    public List<String> getCityByCountry(String country) {
        return customerRepository.findCitiesByCountry(country);
    }

    public List<String> getAllCountries() {
        return customerRepository.findDistinctCountry();
    }

    public List<Customer> getCustomerByCountryAndCity(String country,String city) {
        return customerRepository.findCustomerByCountryAndCity(country,city);
    }

    public List<Customer> getCustomerByCountry(String country) {
        return customerRepository.findCustomerByCountry(country);
    }

    public Customer addCustomer(Customer customer) {
        if(customer == null || customerRepository.existsById(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer id %s customer not exits",customer.getId()));
        }
        return customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        if(customer == null || !customerRepository.existsById(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer id %s customer not exits",customer.getId()));
        }
        customerRepository.save(customer);
    }

    public Customer deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer id %s not found!", customerId));
        }
        customerRepository.deleteById(customerId);
        return customer;
    }

}
