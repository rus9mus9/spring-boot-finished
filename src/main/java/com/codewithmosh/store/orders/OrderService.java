package com.codewithmosh.store.orders;

import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.users.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final AuthService authService;

    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        User currentUser = authService.getCurrentUser();
        List<Order> ordersByCustomer = orderRepository.getOrdersByCustomer(currentUser);
        return ordersByCustomer.stream().map(orderMapper::toOrderDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if (!order.isPlacedBy(authService.getCurrentUser())) {
            throw new OrderAccessException();
        }
        return orderMapper.toOrderDto(order);
    }
}
