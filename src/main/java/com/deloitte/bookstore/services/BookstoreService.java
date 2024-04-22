package com.deloitte.bookstore.services;

import com.deloitte.bookstore.daos.Book;
import com.deloitte.bookstore.daos.Customer;
import com.deloitte.bookstore.dtos.BookstoreRequestDto;
import com.deloitte.bookstore.dtos.BookstoreResponseDto;
import com.deloitte.bookstore.dtos.InventoryRequestDto;
import com.deloitte.bookstore.exceptions.ResourceNotFoundException;
import com.deloitte.bookstore.exceptions.ResourceOutOfStockException;
import com.deloitte.bookstore.repositories.CustomerRepository;
import com.deloitte.bookstore.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookstoreService {

    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;

    public BookstoreService(CustomerRepository repository, InventoryRepository bookRepository) {
        this.customerRepository = repository;
        this.inventoryRepository = bookRepository;
    }

    public BookstoreResponseDto addToCart(Long id, BookstoreRequestDto request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        Book book = inventoryRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException(request.getBookId()));

        customer.getCart().add(book);
        customerRepository.save(customer);

        return BookstoreResponseDto.builder()
                .userId(id)
                .cart(customer.getCart().stream().map(InventoryRequestDto::new).collect(Collectors.toSet()))
                .build();
    }

    public void deleteFromCart(Long id, BookstoreRequestDto request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        Set<Book> newCart = customer.getCart().stream().filter(book -> !book.getBookId().equals(request.getBookId())).collect(Collectors.toSet());

        customer.setCart(newCart);
        customerRepository.save(customer);
    }

    public BookstoreResponseDto getCart(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        return BookstoreResponseDto.builder()
                .userId(id)
                .cart(customer.getCart().stream().map(InventoryRequestDto::new).collect(Collectors.toSet()))
                .build();
    }

    public BookstoreResponseDto checkOut(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        float total = 0;
        for (Book book: customer.getCart()) {
            if (book.getStock() == 0) {
                throw new ResourceOutOfStockException(book.getBookId());
            }
            book.setStock(book.getStock()-1);
            total += book.getPrice();
        }

        BookstoreResponseDto response = BookstoreResponseDto.builder()
                .userId(id)
                .total(total)
                .cart(customer.getCart().stream().map(InventoryRequestDto::new).collect(Collectors.toSet()))
                .build();

        inventoryRepository.saveAll(customer.getCart());
        customer.setCart(new HashSet<>());
        customerRepository.save(customer);

        return response;
    }

}
