package com.example.mangashop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ActiveShoppingCartAlreadyExist extends RuntimeException {
    public ActiveShoppingCartAlreadyExist(){
        super("ActiveShoppingCartAlreadyExist");
    }
}
