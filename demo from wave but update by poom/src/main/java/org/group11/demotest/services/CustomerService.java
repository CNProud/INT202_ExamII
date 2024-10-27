package org.group11.demotest.services;

import org.group11.demotest.entities.Customer;
import org.group11.demotest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public void createCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id already exist");
        }
        customerRepository.save(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer deleteCustomer(int id) {
        var customer = customerRepository.findById(id).orElse(null);
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id is not exist");
        }
        customerRepository.delete(customer);
        return customer;
    }

    public void updateCustomer(Customer customer) {
        if (!customerRepository.existsById(customer.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id is not exist");
        }
        customerRepository.save(customer);
    }

    public List<Customer> searchBetween(int upper, int lower) {
        // In case upper and lower is swaped
        if (upper < lower) {
            var temp = upper;
            upper = lower;
            lower = temp;
        }
        return customerRepository.findByIdBetween(lower,upper);
    }

    public List<Customer> searchContent(String content) {
        return customerRepository.findContentContain(content);
    }

    public List<Customer> searchMix(String content,int upper, int lower) {
        if (upper < lower) {
            var temp = upper;
            upper = lower;
            lower = temp;
        }
        return customerRepository.findMix(content, lower, upper);
    }
}
