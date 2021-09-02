package com.example.mangashop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(Long id) {
    super(String.format("Product with id %d is out of stock",id));
    }

}
