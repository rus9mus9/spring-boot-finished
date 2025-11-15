package com.codewithmosh.store.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDtoForCreateUpdate {
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
