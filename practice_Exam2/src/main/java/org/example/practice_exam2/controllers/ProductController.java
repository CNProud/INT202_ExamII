package org.example.practice_exam2.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.practice_exam2.entities.Product;
import org.example.practice_exam2.services.ProductLineService;
import org.example.practice_exam2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductLineService productLineService;

    @GetMapping("/all")
    public String allProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "product_list";
    }

    @PostMapping("/add")
    public void addProduct(Product product, HttpServletResponse response) throws IOException {
        productService.addProduct(product);
        response.sendRedirect("/products/all");
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam String productCode, Model model) {
        model.addAttribute("product", productService.deleteProduct(productCode));
        model.addAttribute("message", "Product deleted successfully");
        return "product_detail";
    }

    @GetMapping("")
    public String getProductById(@RequestParam String productCode, Model model) {
        model.addAttribute("product", productService.findProductById(productCode));
        return "product_detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("productLines", productLineService.getProductLines());
        return "create_form";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam String productCode, Model model) {
        model.addAttribute("product", productService.findProductById(productCode));
        model.addAttribute("productLines", productLineService.getProductLines());
        return "update_form";
    }

    @PostMapping("/update")
    public void updateProduct(Product product, HttpServletResponse response) throws IOException {
        productService.updateProduct(product);
        response.sendRedirect("/products/all");
    }

    @GetMapping("/search")
    public String searchProductByAnyContent(@RequestParam String searchParam, Model model) {
        model.addAttribute("products", productService.findByAnyContent(searchParam));
        return "product_list";
    }

    @GetMapping("/searchByPrice")
    public String searchProductByPrice(@RequestParam(defaultValue = "1.00") Double minPrice, @RequestParam(defaultValue = "999.00") Double maxPrice, Model model) {
        model.addAttribute("products", productService.findProductsByPrice(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice)));
        return "product_list";
    }
}
