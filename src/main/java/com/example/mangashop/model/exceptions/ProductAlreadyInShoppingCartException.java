package com.example.mangashop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ProductAlreadyInShoppingCartException extends Throwable {
    public ProductAlreadyInShoppingCartException(String name) {
        super(String.format("Product %s is already in active shopping cart",name));
    }
}
