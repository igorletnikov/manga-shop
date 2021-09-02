package com.example.mangashop.service;

import com.example.mangashop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
//    List<Product> findAllByManufacturerId(Long manufacturerId);
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product, MultipartFile image) throws IOException;
    Product update(Long id,Product product,MultipartFile image) throws IOException;
    void deleteById(Long id);
//    List<Product> findAll();
//    List<Product> findAllByManufacturerId(Long manufacturerId);
//    List<Product> findAllSortedByPrice(boolean asc);
//    Product findById(Long id);
//    Product saveProduct(String name, Float price, Integer quantity, Long manufacturerId);
//    Product save(Product product, MultipartFile image) throws IOException;
//    Product update(Long id, Product product, MultipartFile image) throws IOException;
//    void deleteById(Long id);





}
