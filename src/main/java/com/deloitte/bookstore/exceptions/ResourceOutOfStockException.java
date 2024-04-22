package com.deloitte.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceOutOfStockException extends RuntimeException {
    public ResourceOutOfStockException(Long id) {
        super("Insufficient stock: " + id);
    }
}
