package com.deloitte.bookstore.controllers;

import com.deloitte.bookstore.dtos.InventoryRequestDto;
import com.deloitte.bookstore.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Implements
 * 1. Book Management
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

@PostMapping("")
    public ResponseEntity<InventoryRequestDto> addBook(
            @Valid @RequestBody InventoryRequestDto newBook
    ) {
        return new ResponseEntity<>(inventoryService.addBook(newBook), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InventoryRequestDto> updateBook(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody InventoryRequestDto updatedBook) {
        return new ResponseEntity<>(inventoryService.updateBook(id, updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable(value = "id") Long id) {
        inventoryService.deleteBook(id);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryRequestDto> getBook(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(inventoryService.getBook(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<InventoryRequestDto>> getBooks(
            Pageable pageable,
            @RequestParam(required = false, value = "title") String title,
            @RequestParam(required = false, value = "author") String author) {
        return new ResponseEntity<>(inventoryService.getBooks(pageable, title, author), HttpStatus.OK);
    }

}
