package sit.int202.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sit.int202.demo.entities.Product;
import sit.int202.demo.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Product> findByAnyContent(String anyContent) {
        return repository.findProductsByProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(anyContent, anyContent, anyContent);
    }

    public List<Product> findByAnyContentUsingNameQuery(String anyContent) {
        return repository.findByAnyContents("%" + anyContent + "%");
    }

    public List<Product> findByPriceBetween(BigDecimal lower, BigDecimal upper) {
        if(lower.compareTo(upper) > 0) {
            BigDecimal temp = lower;
            lower = upper;
            upper = temp;
        }
        return repository.findProductsByBuyPriceBetweenOrderByBuyPriceDesc(lower, upper);
    }
}
