package com.example.mangashop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ManufaturerNotFoundException extends RuntimeException {
    public ManufaturerNotFoundException(Long id) {
        super(String.format("Manufacturer %d ne e najden!",id));

    }
}
