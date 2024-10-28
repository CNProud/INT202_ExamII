package sit.int202.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int202.demo.entities.Productline;

import java.util.List;

public interface ProductlineRepository extends JpaRepository<Productline, String> {
    @Query("SELECT p.productLine FROM Productline p")
    List<String> findAllProductLine();
}