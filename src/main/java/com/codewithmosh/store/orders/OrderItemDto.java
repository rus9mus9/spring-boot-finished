package com.codewithmosh.store.orders;

import com.codewithmosh.store.products.ProductSimpleDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private ProductSimpleDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
