package org.example.pretest.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pretest.entities.Customer;
import org.example.pretest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public String allCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("countries", customerService.getAllCountries());
        return "customer_list";
    }

    @GetMapping("/create")
    public String formCustomer() {
        return "customer_create";
    }

    @PostMapping("/create")
    public void createCustomer(Customer customer, HttpServletResponse response) throws IOException {
        customerService.addCustomer(customer);
        response.sendRedirect("/customers/all");
    }

    @GetMapping("/update")
    public void

    @GetMapping("/search")
    public String searchCustomers(@RequestParam String searchCustomerContents, Model model) {
        model.addAttribute("customers", customerService.searchCustomersByContent(searchCustomerContents));
        return "customer_list";
    }

    @GetMapping("/filter")
    public String filterCustomer(@RequestParam String country, @RequestParam(required = false) String city, Model model) {
        List<String> countries = customerService.getAllCountries();
        List<String> cities = (country != null) ? customerService.getCityByCountry(country) : List.of();
        List<Customer> customers = null;

        model.addAttribute("countries", countries);
        model.addAttribute("cities", cities);
        model.addAttribute("selectedCountry", country);
        model.addAttribute("selectedCity", city);

        if(country != null && city != null) {
            customers = customerService.getCustomerByCountryAndCity(country, city);
        } else if (country != null) {
            customers = customerService.getCustomerByCountry(country);
        }
        model.addAttribute("customers", customers);
        return "customer_list";
    }

}
