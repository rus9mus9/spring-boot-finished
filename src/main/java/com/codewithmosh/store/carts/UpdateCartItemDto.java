package com.codewithmosh.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemDto {
    @NotNull(message = "Quantity must be provided.")
    @Min(value = 1, message = "Must be between 1 and 100")
    @Max(value = 100, message = "Must be between 1 and 100")
    private Integer quantity;
}
