package com.deloitte.bookstore.dtos;

import com.deloitte.bookstore.daos.Book;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InventoryRequestDto {

    public InventoryRequestDto(Book book) {
        this.id = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.stock = book.getStock();
        this.price = book.getPrice();
    }

    @Null
    private Long id;

    @NotNull
    @Size(min=1, max=50)
    private String title;

    @NotNull
    @Size(min=1, max=50)
    private String author;

    @NotNull
    @Min(value = 0, message = "Stock must be greater than or equal to zero.")
    private int stock;

    @NotNull
    @Min(value = 0)
    private float price;

}
