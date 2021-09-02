package com.example.mangashop.service.impl;

import com.example.mangashop.model.Manufacturer;
import com.example.mangashop.model.Product;
import com.example.mangashop.model.exceptions.ProductNotFoundException;
import com.example.mangashop.repository.ProductRepository;
import com.example.mangashop.service.ManufacturerService;
import com.example.mangashop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
    }




    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

    }

    @Override
    public Product save(Product product, MultipartFile image) throws IOException {
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId());
        product.setManufacturer(manufacturer);
        if (image != null) {
            byte[] imageBytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(imageBytes));
            product.setBase64Image(base64Image);
        }
        return this.productRepository.save(product);

    }

    @Override
    public Product update(Long id, Product product, MultipartFile image) throws IOException {
        Product updatedProduct = this.findById(id);
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId());
        updatedProduct.setManufacturer(manufacturer);
        updatedProduct.setName(product.getName());
        updatedProduct.setQuantity(product.getQuantity());
        updatedProduct.setPrice(product.getPrice());
        if (image != null) {
            byte[] imageBytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(imageBytes));
            updatedProduct.setBase64Image(base64Image);
        }
        return this.productRepository.save(updatedProduct);

    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
