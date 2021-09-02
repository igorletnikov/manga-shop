package com.example.mangashop.service;

import com.example.mangashop.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    Manufacturer findById(Long id);
    Manufacturer save(Manufacturer manufacturer);
    Manufacturer update(Long id , Manufacturer manufacturer);
    Integer removeById(Long id);
}
