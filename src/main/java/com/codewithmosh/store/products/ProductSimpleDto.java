package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSimpleDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
