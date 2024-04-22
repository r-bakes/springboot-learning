package com.deloitte.bookstore.repositories;

import com.deloitte.bookstore.daos.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitle(Pageable pageable, String title);
    Page<Book> findByTitleAndAuthor(Pageable pageable, String title, String author);
    Page<Book> findByAuthor(Pageable pageable, String author);
}
