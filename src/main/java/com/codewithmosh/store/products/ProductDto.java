package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDto extends ProductSimpleDto {
    private String description;
    private Byte categoryId;
}
