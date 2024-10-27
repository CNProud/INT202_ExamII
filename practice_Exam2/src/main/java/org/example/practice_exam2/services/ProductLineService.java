package org.example.practice_exam2.services;

import org.example.practice_exam2.entities.Productline;
import org.example.practice_exam2.repositories.ProductLineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLineService {
    @Autowired
    private ProductLineRepo productLineRepo;

    public List<Productline> getProductLines() {
        return productLineRepo.findAll();
    }
}
