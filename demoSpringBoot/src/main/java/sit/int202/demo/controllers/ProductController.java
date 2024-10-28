package sit.int202.demo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sit.int202.demo.entities.Office;
import sit.int202.demo.entities.Product;
import sit.int202.demo.entities.Productline;
import sit.int202.demo.services.ProductLineService;
import sit.int202.demo.services.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


//    @GetMapping("/page")
//    public String getAllProductsPaging(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, Model model) {
//        model.addAttribute("page", service.findAll(PageRequest.of(pageNumber, pageSize)));
//        return "product_list_paging";
//    }

    @GetMapping("")
    public String getAllProduct(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, Model model) {
        model.addAttribute("page", productService.findAll(PageRequest.of(pageNumber, pageSize)));
        return "product_list"; // Use paging template for consistency
    }

    @GetMapping("/product_details")
    public String getProductById(@RequestParam String productCode, Model model) {
        Product product = productService.getProductById(productCode);
        model.addAttribute("product", product);
        return "product_details";
    }

    @GetMapping("/search")
    public String searchByContent(
            @RequestParam(required = false) String searchParam,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        model.addAttribute("page", productService.findByAnyContent(
                searchParam,
                PageRequest.of(pageNumber, pageSize)));
        model.addAttribute("searchParam", searchParam); // Retain searchParam for pagination
        return "product_list";
    }

    @GetMapping("/searchByPrice")
    public String searchByPrice(
            @RequestParam(defaultValue = "10.0") Double lower,
            @RequestParam(defaultValue = "9999.99") Double upper,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        model.addAttribute("page", productService.findByPrice(
                BigDecimal.valueOf(lower),
                BigDecimal.valueOf(upper),
                PageRequest.of(pageNumber, pageSize)));
        // Retain price parameters for pagination
        model.addAttribute("lower", lower);
        model.addAttribute("upper", upper);
        return "product_list";
    }

    @GetMapping("/searchByContentAndPrice")
    public String searchAndContentByPrice(
            @RequestParam(required = false) String searchParam,
            @RequestParam(defaultValue = "10.0") Double lower,
            @RequestParam(defaultValue = "9999.99") Double upper,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {
        // Call the service with the search criteria
        model.addAttribute("page", productService.findProductsByPriceAndSearchCriteria(
                searchParam,
                BigDecimal.valueOf(lower),
                BigDecimal.valueOf(upper),
                PageRequest.of(pageNumber, pageSize)));
        // Retain search parameters for pagination
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("lower", lower);
        model.addAttribute("upper", upper);
        return "product_list";
    }



    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        List<String> productLines = productService.getAllProductline();
        model.addAttribute("productLine", productLines);
        return "add_Product";
    }

    @PostMapping("/add")
    public void addProduct(Product product, String productLine, HttpServletResponse response) throws IOException {
        productService.addProduct(product, productLine);

        response.sendRedirect("/products");
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam String productCode, Model model) {
        List<String> productLines = productService.adjustProductLines(productCode);
        Product product = productService.getProductById(productCode);
        model.addAttribute("product", product);
        model.addAttribute("productLines", productLines);
        model.addAttribute("selectedProductLine", product.getProductLine() != null ? product.getProductLine().getProductLine() : null);

        return "update_product";
    }

    @PostMapping("/update")
    public void updateProduct(Product product, HttpServletResponse response) throws IOException {
        productService.updateProduct(product);
        response.sendRedirect("/products");
    }

    @GetMapping("/delete")
    public String deleteProductById(@RequestParam String productCode, Model model){
        Product product = productService.deleteProduct(productCode);
        model.addAttribute("product", product);
        model.addAttribute("message", "Office deleted successfully");
        return "product_details";

    }
}