package com.example.mangashop.web.rest;

import com.example.mangashop.model.Manufacturer;
import com.example.mangashop.service.ManufacturerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/manufacturers")
public class ManufacturerRestController {
    private final ManufacturerService manufacturerService;

    public ManufacturerRestController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<Manufacturer> findAll(){
        return this.manufacturerService.findAll();
    }

    @GetMapping("/{id}")
    public Manufacturer findById(@PathVariable Long id){
        return this.manufacturerService.findById(id);
    }

    @PostMapping
    public Manufacturer save(@Valid Manufacturer manufacturer){
        return this.manufacturerService.save(manufacturer);
    }
    @PutMapping("/{id}")
    public Manufacturer update(@PathVariable Long id , @Valid Manufacturer manufacturer){
        return this.manufacturerService.update(id,manufacturer);
    }

    @DeleteMapping("/{id}")
    public Integer removeById(@PathVariable Long id){
        return this.manufacturerService.removeById(id);

    }



}
