package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemMapper {

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemSimpleDto toSimpleDto(CartItem cartItem);

    void update(UpdateCartItemDto updateCartItemDto, @MappingTarget CartItem cartItem);
}
