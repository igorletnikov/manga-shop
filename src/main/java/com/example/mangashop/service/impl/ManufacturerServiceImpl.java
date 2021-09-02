package com.example.mangashop.service.impl;

import com.example.mangashop.model.Manufacturer;
import com.example.mangashop.model.exceptions.ManufaturerNotFoundException;
import com.example.mangashop.model.exceptions.ThereAreProductWithThisManufacturerException;
import com.example.mangashop.repository.ManufacturerRepository;
import com.example.mangashop.repository.ProductRepository;
import com.example.mangashop.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ProductRepository productRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id).orElseThrow(()->new ManufaturerNotFoundException(id));
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Long id, Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = this.findById(id);
        updatedManufacturer.setAddress(manufacturer.getAddress());
        updatedManufacturer.setName(manufacturer.getName());
        return this.manufacturerRepository.save(updatedManufacturer);

    }

    @Override
    public Integer removeById(Long id) {
        Manufacturer manufacturer = this.findById(id);
//        if (this.productRepository.existsByManufacturerId(id)) {
//            throw new ThereAreProductWithThisManufacturerException(id);
//        }
        if (manufacturer.getProducts().size() > 0) {
            throw new ThereAreProductWithThisManufacturerException(id);
        }


        return this.manufacturerRepository.removeById(id);
    }

}

