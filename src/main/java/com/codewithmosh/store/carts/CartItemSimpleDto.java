package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductSimpleDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemSimpleDto {
    private ProductSimpleDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
