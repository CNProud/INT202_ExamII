package sit.int202.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.demo.entities.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findProductByBuyPriceBetweenOrderByBuyPriceDesc(BigDecimal lower, BigDecimal upper, Pageable pageable);

    Product findProductsByProductCode(String productCode);


    Page<Product> findProductByProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(
            String productName, String productCode, String productDescription, Pageable pageable);
    @Query("""
            select p from Product p where p.productCode like ?1 
            or p.productName like ?1 
            or p.productDescription like ?1
            or p.productVendor like ?1
            
            """)
    Page<Product> findByAnyContents(String searchParam, Pageable pageable);

    Page<Product> findProductByBuyPriceBetweenAndProductNameContainingOrProductCodeContainingOrProductDescriptionContaining(
            BigDecimal lower, BigDecimal upper,
            String productName, String productCode, String productDescription,
            Pageable pageable);


}