package org.example.practice_exam2.services;

import org.example.practice_exam2.entities.Product;
import org.example.practice_exam2.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(String productId) {
        return productRepository.findByProductCode(productId);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product deleteProduct(String productCode) {
        Product productFound = findProductById(productCode);
        if(productFound == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Can't delete, Product with code %s not found", productCode));
        }
        productRepository.deleteById(Integer.valueOf(productCode));
        return productFound;
    }

    public Product addProduct(Product product) {
        if(product.getProductCode() == null || productRepository.existsById(Integer.valueOf(product.getProductCode()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    ,String.format("Office id '%s' already exists", product.getProductCode()));
        }
        return productRepository.save(product);
    }

    public void updateProduct(Product product) {
        if(product.getProductCode() == null || !productRepository.existsById(Integer.valueOf(product.getProductCode()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Product code '%s' does not exists", product.getProductCode()));
        }
        productRepository.save(product);
    }

    public List<Product> findByAnyContent(String content) {
        return productRepository.findByAnyContents("%" + content + "%");
    }

    public List<Product> findProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if(minPrice.compareTo(maxPrice) > 0) {
            BigDecimal temp = minPrice;
            minPrice = maxPrice;
            maxPrice = temp;
        }
        return productRepository.findProductsByBuyPriceBetweenOrderByBuyPriceAsc(minPrice, maxPrice);
    }
}
