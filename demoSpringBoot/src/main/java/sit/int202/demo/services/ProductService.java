package sit.int202.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int202.demo.entities.Product;
import sit.int202.demo.entities.Productline;
import sit.int202.demo.repositories.ProductRepository;
import sit.int202.demo.repositories.ProductlineRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductlineRepository productlineRepository;

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(String productCode){
        return productRepository.findProductsByProductCode(productCode);
    }

    public Page<Product> findByAnyContentUsingNameQuery(String anyContent, Pageable pageable) {
        return productRepository.findByAnyContents("%" + anyContent + "%", pageable);
    }

    public Page<Product> findByAnyContent(String anyContent, Pageable pageable) {
        return productRepository.findProductByProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(anyContent, anyContent, anyContent, pageable);
    }

    public Page<Product> findByPrice(BigDecimal lower, BigDecimal upper, Pageable pageable) {
        if (lower.compareTo(upper) > 0) {
            BigDecimal tmp = lower;
            lower = upper;
            upper = tmp;
        }
        return productRepository.findProductByBuyPriceBetweenOrderByBuyPriceDesc(lower, upper, pageable);
    }

    public Page<Product> findProductsByPriceAndSearchCriteria(String anyContent, BigDecimal lower, BigDecimal upper, Pageable pageable) {
        if (lower.compareTo(upper) > 0) {
            BigDecimal tmp = lower;
            lower = upper;
            upper = tmp;
        }
        return productRepository.findProductByBuyPriceBetweenAndProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(lower, upper, anyContent, anyContent, anyContent , pageable);
    }

//    public Product addProduct(Product product) {
//        if (product.getProductCode() == null || product.getProductCode().isEmpty() || productRepository.existsById(product.getProductCode())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    String.format("Product id %s already exists", product.getProductCode()));
//        }
//        return productRepository.save(product);
//    }

    public List<String> getAllProductline() {
        return productlineRepository.findAllProductLine();
    }
    public void addProduct(Product product, String productLineId) {
        Productline selectedLine = productlineRepository.findById(productLineId).orElse(null);
        if (selectedLine != null) {
            product.setProductLine(selectedLine); // Associate the product with the product line
            productRepository.save(product); // Save the product to the database
        } else {
            // Log or handle the case where the product line does not exist
            System.out.println("Product Line not found: " + productLineId);
        }
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        if (product.getProductCode() == null || product.getProductCode().isEmpty() || !productRepository.existsById(product.getProductCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't update, Product id %s does not exists", product.getProductCode()));
        }
        return productRepository.save(product);
    }


    public List<String> adjustProductLines(String productCode) {
        Product product = getProductById(productCode);
        List<String> productLines = getAllProductline();
        String currentProductLine = product.getProductLine() != null ? product.getProductLine().getProductLine() : null;

        if (!productLines.isEmpty() && currentProductLine != null) {
            if (productLines.contains(currentProductLine)) {
                productLines.remove(currentProductLine);
                productLines.add(0, currentProductLine);
            }
        }
        return productLines;
    }
    public Product deleteProduct(String productCode) {
        Product product = getProductById(productCode);
        if( product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't delete, Product id %s does not exists", productCode));
        }
        productRepository.deleteById(productCode);
        return product;
    }

}