package com.example.mangashop.web.controller;

import com.example.mangashop.model.Manufacturer;
import com.example.mangashop.model.Product;
import com.example.mangashop.service.ManufacturerService;
import com.example.mangashop.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    public final ProductService productService;
    public final ManufacturerService manufacturerService;
    public ProductController(ProductService productService, ManufacturerService manufacturerService){
        this.productService = productService;
        this.manufacturerService = manufacturerService;
    }





    @GetMapping
    public String listProducts(Model model){
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }
//    SAVE
    @PostMapping
    @Secured("ROLE_ADMIN")
    public String saveOrUpdateProduct(
            Model model,
            @Valid Product product,
            BindingResult bindingResult,
            @RequestParam MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            return "add-product";
        }

        try {
            if (product.getId() == null) {
                this.productService.save(product, image);
            } else {
                this.productService.update(product.getId(), product, image);
            }
            return "redirect:/products";
        } catch (RuntimeException ex) {
            return "redirect:/products/add-new?error=" + ex.getLocalizedMessage();
        }
    }

//    ADD NEW
    @GetMapping("/add-new")
    public String addNewProductPage(Model model){
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("product",new Product());
        model.addAttribute("manufacturers",manufacturers);
        return "add-new";
    }
//    EDIT
    @GetMapping("/{id}/edit")
    public String editProductPage(@PathVariable Long id, Model model) {
        try {
            Product product = this.productService.findById(id);
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("product", product);
            model.addAttribute("manufacturers", manufacturers);
            return "add-new";
        } catch (RuntimeException ex) {
            return "redirect:/products?error="+ ex.getLocalizedMessage();
        }
    }
//    DELETE
    @PostMapping("/{id}/delete")
    public String deleteProductWithPost(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }
}




