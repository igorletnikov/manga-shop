package com.example.mangashop.model;

import com.example.mangashop.model.enumerations.CartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.CREATED;

    //many to one - eden korisnik moze da ima poveke shoping karti , dodeka edna shoping kart e na eden korisnik
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //edna shoping kart moze da ima poveke producti i obratno
    @ManyToMany
    @JoinTable(name = "cart_product"
            ,joinColumns = @JoinColumn(name = "cart_id")
            ,inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
