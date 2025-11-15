package com.codewithmosh.store.carts;

public class CartNotFoundExceptionForOrder extends RuntimeException {
    public CartNotFoundExceptionForOrder() {
        super("Cart not found for order");
    }
}
