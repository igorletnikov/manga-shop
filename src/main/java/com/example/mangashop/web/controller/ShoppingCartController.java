package com.example.mangashop.web.controller;

import com.example.mangashop.model.Product;
import com.example.mangashop.model.ShoppingCart;
import com.example.mangashop.model.exceptions.ProductAlreadyInShoppingCartException;
import com.example.mangashop.service.AuthService;
import com.example.mangashop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping
    public String getShoppingCartPage(Model model){
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(this.authService.getCurrentUserId());
        List<Product> items=shoppingCart.getProducts();
        model.addAttribute("items",items);
        return "shopping-cart";
    }

    @PostMapping("/{id}/add-new")
    public String addGuitarToShoppingCart(@PathVariable Long id) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(
                    this.authService.getCurrentUserId(), id);
        } catch (RuntimeException | ProductAlreadyInShoppingCartException ex) {
            return "redirect:/products?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/products";
    }


    @PostMapping("/{id}/remove-product")
    public String removeGuitarToShoppingCart(@PathVariable Long id) {
        ShoppingCart shoppingCart = this.shoppingCartService.removeProductFromShoppingCart(this.authService.getCurrentUserId(), id);
        return "redirect:/products";
    }

}
