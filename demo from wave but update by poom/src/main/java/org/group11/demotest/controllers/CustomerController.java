package org.group11.demotest.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.group11.demotest.entities.Customer;
import org.group11.demotest.services.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public String getAllCustomer(Model model) {
        var customers = customerService.getAllCustomers();
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("")
    public String getCustomerDetail(@RequestParam int id, Model model){
        var customer = customerService.getCustomerById(id);
        model.addAttribute("customer",customer);
        return "customer_detail";
    }

    @GetMapping("/create")
    public String createForm() {
        return "create_form";
    }

    @PostMapping("/create")
    public void createCustomer(Customer customer, HttpServletResponse httpServletResponse) throws IOException {
        customerService.createCustomer(customer);
        httpServletResponse.sendRedirect("/customers/all");
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam int id,Model model) {
        var customer = customerService.deleteCustomer(id);
        model.addAttribute("customer",customer);
        return "customer_detail";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam int id,Model model) {
        var customer = customerService.getCustomerById(id);
        model.addAttribute("customer",customer);
        return "update_form";
    }

    @PostMapping("/update")
    public void updateCustomer(Customer customer, HttpServletResponse httpServletResponse) throws IOException {
        customerService.updateCustomer(customer);
        httpServletResponse.sendRedirect("/customers?id="+customer.getId());
    }

    @GetMapping("/searchBetween")
    public String searchBetween(@RequestParam int upper, @RequestParam int lower,Model model) {
        var customers = customerService.searchBetween(upper,lower);
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("/searchContent")
    public String searchContent(@RequestParam String content,Model model) {
        var customers = customerService.searchContent(content);
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("/searchMix")
    public String searchContent(@RequestParam String content,
                                @RequestParam int upper,
                                @RequestParam int lower, Model model) {
        var customers = customerService.searchMix(content,upper,lower);
        model.addAttribute("customers",customers);
        return "customer_list";
    }

    @GetMapping("/page")
    public String pageContent(@RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "0") int pageNumber,Model model) {
        var customers = customerService.getAllCustomers(PageRequest.of(pageNumber,pageSize));
        model.addAttribute("customers",customers);
        return "customer_list_page";
    }


}
