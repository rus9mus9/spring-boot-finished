package com.codewithmosh.store.orders;

import com.codewithmosh.store.payments.CheckoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderId", source = "id")
    CheckoutResponse toDto(Order order);

    @Mapping(target = "items", source = "orderItems")
    OrderDto toOrderDto(Order order);
}
