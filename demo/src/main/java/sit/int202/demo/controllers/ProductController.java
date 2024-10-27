package sit.int202.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.services.ProductService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("")
    public String getAllProducts(Model model) {
        model.addAttribute("products", service.findAll());
        return "product_list"; // return ชื่อ view (ใน folder templates)
    }

    @GetMapping("/search")
    public String searchByContent(@RequestParam String searchParam, Model model){
        model.addAttribute("products", service.findByAnyContentUsingNameQuery(searchParam));
        return "product_list";
    }

    @GetMapping("/searchByPrice")
    public String searchByPrice(@RequestParam(defaultValue = "10.0") Double lower,
                                @RequestParam(defaultValue = "9999.99") Double upper,
                                Model model) {
        model.addAttribute("products", service.findByPriceBetween(BigDecimal.valueOf(lower), BigDecimal.valueOf(upper)));
        return "product_list";
    }

    @GetMapping("/page")
    public String getAllProductsPaging(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize,
                                       Model model) {
        model.addAttribute("page", service.findAll(PageRequest.of(pageNumber, pageSize)));
        return "product_list_paging";
    }
}
