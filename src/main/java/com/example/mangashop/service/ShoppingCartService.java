package com.example.mangashop.service;

import com.example.mangashop.model.DataTransferObject.ChargeRequest;
import com.example.mangashop.model.ShoppingCart;
import com.example.mangashop.model.exceptions.ProductAlreadyInShoppingCartException;
import com.example.mangashop.model.exceptions.TransactionFailedException;

public interface ShoppingCartService {
    ShoppingCart findActiveShoppingCartByUsername(String userId);
    ShoppingCart createShoppingCart(String userId);
    ShoppingCart addProductToShoppingCart(String userId, Long productId) throws ProductAlreadyInShoppingCartException;
    ShoppingCart removeProductFromShoppingCart(String userId, Long productId);
    ShoppingCart getActiveShoppingCart(String userId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws TransactionFailedException;


}
