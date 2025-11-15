package com.codewithmosh.store.carts;

import com.codewithmosh.store.products.ProductNotFoundException;
import com.codewithmosh.store.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    private CartMapper cartMapper;
    private CartItemMapper cartItemMapper;


    public CartDto createCart() {
        var cart = cartRepository.save(new Cart());
        return cartMapper.toDto(cart);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);
        return cartMapper.toDto(cart);
    }

    public CartItemSimpleDto addProductToCart(UUID cartId, Long productId) {

        // Check if cart exists
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);

        // Check if product exists
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartItemMapper.toSimpleDto(cartItem);
    }

    public CartItemSimpleDto updateCartItem(UUID cartId, Long productId, UpdateCartItemDto updateCartItemDto) {

        // Check if cart exists
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);

        // Finding the cart item to update
        var cartItem2Update = cart.getItem(productId);
        if (cartItem2Update == null) {
            throw new ProductNotFoundException();
        }

        cartItemMapper.update(updateCartItemDto, cartItem2Update);
        cartRepository.save(cart);


        return cartItemMapper.toSimpleDto(cartItem2Update);
    }

    public void deleteCartItem(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    
    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);
        cart.clear();
        cartRepository.save(cart);
    }

    public void checkoutCart(UUID cartId) {
        // check if cart exists, 400 otherwise
        var cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundExceptionForOrder::new);

        // if cart is empty 400
        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException();
        }

        // create an order entity, save it, clear the cart, return 200

    }

}
