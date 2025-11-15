package com.codewithmosh.store.payments;

import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.carts.CartEmptyException;
import com.codewithmosh.store.carts.CartNotFoundExceptionForOrder;
import com.codewithmosh.store.carts.CartRepository;
import com.codewithmosh.store.orders.OrderRepository;

@RequiredArgsConstructor
@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;

    private final CartService cartService;

    private final PaymentGateway paymentGateway;

    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        var cartId = checkoutRequest.getCartId();

        // check if cart exists, 400 otherwise
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundExceptionForOrder::new);

        // if cart is empty 400
        if (cart.isCartItemsEmpty()) {
            throw new CartEmptyException();
        }

        Order order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cartId);
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

        } catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
            .parseWebhookRequest(request)
            .ifPresent(paymentResult -> {
                var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                order.setStatus(paymentResult.getPaymentStatus());
                orderRepository.save(order);
            });
    }
}
