package sit.int202.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int202.demo.entities.Product;
import sit.int202.demo.entities.Productline;
import sit.int202.demo.repositories.ProductRepository;
import sit.int202.demo.repositories.ProductlineRepository;

import java.util.List;

@Service
public class ProductLineService {
    @Autowired
    private ProductlineRepository productlineRepository;
    @Autowired
    private ProductRepository productRepository;



}
