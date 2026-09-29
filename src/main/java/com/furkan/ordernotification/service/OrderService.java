package com.furkan.ordernotification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furkan.ordernotification.exception.OrderNotFoundException;
import com.furkan.ordernotification.model.Order;
import com.furkan.ordernotification.model.OrderStatus;
import com.furkan.ordernotification.observer.OrderEventPublisher;
import com.furkan.ordernotification.observer.OrderStatusChangedEvent;
import com.furkan.ordernotification.repository.OrderRepository;
import com.furkan.ordernotification.exception.OrderNotFoundException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final OrderStatusTransitionValidator transitionValidator;


    public OrderService(
        OrderRepository orderRepository,
        OrderEventPublisher orderEventPublisher,
        OrderStatusTransitionValidator transitionValidator
    ) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.transitionValidator = transitionValidator;
    }


    public Order createOrder(
        Order order
    ) {
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Order getOrderById(
        Long id
    ) {
        return orderRepository.findById(id)
            .orElseThrow(
                () -> new OrderNotFoundException(id)
            );
    }


    public Order updateOrderStatus(
        Long id,
        OrderStatus newStatus
    ) {
        Order order = getOrderById(id);

        OrderStatus oldStatus = order.getStatus();

        transitionValidator.validate(
            oldStatus,
            newStatus
        );

        order.setStatus(newStatus);

        Order updatedOrder =
            orderRepository.save(order);

        OrderStatusChangedEvent event =
            new OrderStatusChangedEvent(
                updatedOrder.getId(),
                updatedOrder.getCustomerEmail(),
                oldStatus,
                newStatus
            );

        orderEventPublisher.notifyObservers(event);

        return updatedOrder;
    }
}