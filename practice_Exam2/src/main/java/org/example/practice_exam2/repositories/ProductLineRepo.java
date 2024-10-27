package org.example.practice_exam2.repositories;

import org.example.practice_exam2.entities.Productline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepo extends JpaRepository<Productline, String> {
}
