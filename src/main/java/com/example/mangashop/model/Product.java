package com.example.mangashop.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @NotNull
    private Manufacturer manufacturers;

    public Manufacturer getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Manufacturer manufacturers) {
        this.manufacturers = manufacturers;
    }

    private Float price;
    private Integer quantity;

    @Column(name = "image")
    private String base64Image;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Manufacturer getManufacturer() {
        return manufacturers;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturers = manufacturer;
    }
}
