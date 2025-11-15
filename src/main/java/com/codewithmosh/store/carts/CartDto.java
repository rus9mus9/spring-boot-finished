package com.codewithmosh.store.carts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CartDto {
    private UUID id;
    private Set<CartItemSimpleDto> items;
    private BigDecimal totalPrice;
}
