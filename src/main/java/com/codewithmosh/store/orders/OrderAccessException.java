package com.codewithmosh.store.orders;

public class OrderAccessException extends RuntimeException {
    public OrderAccessException() {
        super("Order belongs to another user");
    }
}
