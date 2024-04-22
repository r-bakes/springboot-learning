package com.deloitte.bookstore.controllers;

import com.deloitte.bookstore.dtos.BookstoreRequestDto;
import com.deloitte.bookstore.dtos.BookstoreResponseDto;
import com.deloitte.bookstore.services.BookstoreService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Implements
 * 1. Shopping cart
 * 2. Order processing
 */
@RestController
@RequestMapping("/bookstore")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<BookstoreResponseDto> addToCart(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody BookstoreRequestDto addRequest) {

        return new ResponseEntity<>(bookstoreService.addToCart(id, addRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFromCart(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody BookstoreRequestDto deleteRequest) {

        bookstoreService.deleteFromCart(id, deleteRequest);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookstoreResponseDto> getCart(
            @PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(bookstoreService.getCart(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<BookstoreResponseDto> checkoutCart(
            @PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(bookstoreService.checkOut(id), HttpStatus.OK);
    }
}
