package org.example.practice_exam2.repositories;

import org.example.practice_exam2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByProductCode(String productCode);

    @Query("""
         select p from Product p where p.productCode like ?1
         or p.productName like ?1
         or p.productDescription like ?1
         or p.productVendor like ?1
         or p.productScale like ?1
         """)
    List<Product> findByAnyContents(String searchParam);

    List<Product> findProductsByBuyPriceBetweenOrderByBuyPriceAsc(BigDecimal lowerPrice, BigDecimal upperPrice);
}
