package com.example.mangashop.web.rest;

import com.example.mangashop.model.DataTransferObject.ChargeRequest;
import com.example.mangashop.model.Product;
import com.example.mangashop.model.ShoppingCart;
import com.example.mangashop.model.exceptions.ProductAlreadyInShoppingCartException;
import com.example.mangashop.model.exceptions.TransactionFailedException;
import com.example.mangashop.service.AuthService;
import com.example.mangashop.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }


    @PostMapping
    public ShoppingCart createShoppingCart() {
        return this.shoppingCartService.createShoppingCart(this.authService.getCurrentUserId());
    }

    @PatchMapping("/{productId}/products")
    public String addProductToShoppingCart(@PathVariable Long productId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(
                    this.authService.getCurrentUserId(), productId);
        } catch (RuntimeException | ProductAlreadyInShoppingCartException ex) {
            return "redirect:/shopping-cart?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/shopping-cart";

    }

    @DeleteMapping("/{productId}/products")
    public ShoppingCart removeProductFromShoppingCart(@PathVariable Long productId) {
        return this.shoppingCartService.removeProductFromShoppingCart(
                this.authService.getCurrentUserId(),
                productId
        );
    }

    @DeleteMapping
    public ShoppingCart cancelActiveShoppingCart() {
        return this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
    }

    @PostMapping("/checkout")
    public ShoppingCart checkoutShoppingCart() throws TransactionFailedException {
        ChargeRequest chargeRequest=new ChargeRequest();
        chargeRequest.setAmount(0);
        StringBuilder sb=new StringBuilder();
        List<Product> items=this.shoppingCartService.getActiveShoppingCart(this.authService.getCurrentUserId()).getProducts();
        for (Product c:items) {
            chargeRequest.setAmount((int) (chargeRequest.getAmount()+c.getPrice()));
            sb.append("Item: ").append(c.getName()).append("Price: ").append(c.getPrice()).append("Quantity: ").append(c.getQuantity()).append("\n");
        }
        chargeRequest.setDescription(sb.toString());
        chargeRequest.setCurrency("Euro");
        return this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(),
                chargeRequest);
//        return this.shoppingCartService.checkoutShoppingCart(authService.getCurrentUserId());
    }

}
