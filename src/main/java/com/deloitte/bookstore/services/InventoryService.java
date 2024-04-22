package com.deloitte.bookstore.services;

import com.deloitte.bookstore.daos.Book;
import com.deloitte.bookstore.dtos.InventoryRequestDto;
import com.deloitte.bookstore.exceptions.ResourceNotFoundException;
import com.deloitte.bookstore.repositories.InventoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public InventoryRequestDto getBook(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        return new InventoryRequestDto(book);
    }

    public InventoryRequestDto updateBook(Long id, InventoryRequestDto updatedBook) {
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setStock(updatedBook.getStock());
        book.setPrice(updatedBook.getPrice());

        book = repository.save(book);

        return new InventoryRequestDto(book);
    }

    public InventoryRequestDto addBook(InventoryRequestDto newBook) {

        Book book = repository.save(
                Book.builder()
                        .author(newBook.getAuthor())
                        .title(newBook.getTitle())
                        .stock(newBook.getStock())
                        .price(newBook.getPrice())
                        .build()
        );

        return new InventoryRequestDto(book);

    }

    public Page<InventoryRequestDto> getBooks(Pageable pageable, String title, String author) {

        Page<Book> books;
        if (title != null && author == null) {
            books = repository.findByTitle(pageable, title);
        } else if (title == null && author != null) {
            books = repository.findByAuthor(pageable, author);
        } else if (title != null && author != null) {
            books = repository.findByTitleAndAuthor(pageable, title, author);
        } else {
            books = repository.findAll(pageable);
        }
        return books.map(InventoryRequestDto::new);

    }
}
