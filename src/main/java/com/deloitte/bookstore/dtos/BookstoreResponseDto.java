package com.deloitte.bookstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class BookstoreResponseDto {

    private Long userId;
    private Set<InventoryRequestDto> cart;
    private float total;
}
