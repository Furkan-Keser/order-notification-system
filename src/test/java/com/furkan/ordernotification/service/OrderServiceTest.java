package com.furkan.ordernotification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.furkan.ordernotification.model.Order;
import com.furkan.ordernotification.model.OrderStatus;
import com.furkan.ordernotification.observer.OrderEventPublisher;
import com.furkan.ordernotification.observer.OrderStatusChangedEvent;
import com.furkan.ordernotification.repository.OrderRepository;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    private OrderService orderService;


    @BeforeEach
    void setUp() {
        OrderStatusTransitionValidator transitionValidator =
            new OrderStatusTransitionValidator();

        orderService = new OrderService(
            orderRepository,
            orderEventPublisher,
            transitionValidator
        );
    }


    @Test
    void updateOrderStatusShouldSaveOrderAndPublishEvent() {

        Order order = new Order(
            "Furkan Keser",
            "furkan@example.com",
            new BigDecimal("1250.50")
        );

        when(
            orderRepository.findById(1L)
        ).thenReturn(
            Optional.of(order)
        );

        when(
            orderRepository.save(any(Order.class))
        ).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        Order updatedOrder =
            orderService.updateOrderStatus(
                1L,
                OrderStatus.PREPARING
            );

        assertEquals(
            OrderStatus.PREPARING,
            updatedOrder.getStatus()
        );

        verify(orderRepository)
            .save(order);

        ArgumentCaptor<OrderStatusChangedEvent> eventCaptor =
            ArgumentCaptor.forClass(
                OrderStatusChangedEvent.class
            );

        verify(orderEventPublisher)
            .notifyObservers(
                eventCaptor.capture()
            );

        OrderStatusChangedEvent event =
            eventCaptor.getValue();

        assertEquals(
            OrderStatus.CREATED,
            event.getOldStatus()
        );

        assertEquals(
            OrderStatus.PREPARING,
            event.getNewStatus()
        );

        assertEquals(
            "furkan@example.com",
            event.getCustomerEmail()
        );
    }
}