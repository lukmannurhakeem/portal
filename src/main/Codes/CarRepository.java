package com.example.portal.repository;

import com.example.portal.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwner_Email(String email);

    List<Car> findAll();
}
