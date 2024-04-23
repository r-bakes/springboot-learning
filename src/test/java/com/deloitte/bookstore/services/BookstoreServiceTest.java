package com.deloitte.bookstore.services;

import com.deloitte.bookstore.daos.Book;
import com.deloitte.bookstore.daos.Customer;
import com.deloitte.bookstore.dtos.BookstoreRequestDto;
import com.deloitte.bookstore.dtos.BookstoreResponseDto;
import com.deloitte.bookstore.dtos.InventoryRequestDto;
import com.deloitte.bookstore.exceptions.ResourceNotFoundException;
import com.deloitte.bookstore.repositories.CustomerRepository;
import com.deloitte.bookstore.repositories.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookstoreServiceTest {

    @InjectMocks
    BookstoreService bookstoreService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryRepository inventoryRepository;

    @Test
    void addToCart_fails_whenCustomerNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookstoreService.addToCart(1L, new BookstoreRequestDto()),
                "Expected addToCart() to throw Resource not Found Exception.");

        verify(customerRepository).findById(1L);
    }

    @Test
    void addToCart_fails_whenBookNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookstoreService.addToCart(1L, new BookstoreRequestDto(1L)),
                "Expected addToCart() to throw Resource not Found Exception.");

        verify(customerRepository).findById(1L);
        verify(inventoryRepository).findById(1L);
    }

    @Test
    void addToCart_succeeds_whenBookAndCustomerFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer(1L, "test", new HashSet<>())));
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(new Book(1L, "testTitle", "testAuthor", 0, 0, new HashSet<>())));
        when(customerRepository.save(any())).thenReturn(null);

        BookstoreResponseDto actualResponse = bookstoreService.addToCart(1L, new BookstoreRequestDto(1L));

        assertEquals(actualResponse.getCart().size(), 1);
        assertEquals(actualResponse.getCart().stream().toList().getFirst().getId(), 1L);
        assertEquals(actualResponse.getCart().stream().toList().getFirst().getAuthor(), "testAuthor");
        assertEquals(actualResponse.getCart().stream().toList().getFirst().getTitle(), "testTitle");
        assertEquals(actualResponse.getCart().stream().toList().getFirst().getPrice(), 0);

        verify(customerRepository).findById(1L);
        verify(inventoryRepository).findById(1L);
        verify(customerRepository).save(any());
    }
}