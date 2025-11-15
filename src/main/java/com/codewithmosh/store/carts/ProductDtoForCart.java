package com.codewithmosh.store.carts;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductDtoForCart {
    private Long id;
    private String name;
    private BigDecimal price;
}
