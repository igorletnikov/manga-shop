package com.example.mangashop.repository;

import com.example.mangashop.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
//        boolean existsByManufacturerId(Long manufacturerId);

}
