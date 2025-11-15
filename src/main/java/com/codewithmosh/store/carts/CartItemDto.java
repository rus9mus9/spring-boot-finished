package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemDto {
    private Long id;
    private CartDto cartDto;
    private ProductDto productDto;
    private Integer quantity;
}
