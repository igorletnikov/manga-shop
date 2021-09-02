package com.example.mangashop.repository;

import com.example.mangashop.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    @Transactional
    Integer removeById(Long id);
}
