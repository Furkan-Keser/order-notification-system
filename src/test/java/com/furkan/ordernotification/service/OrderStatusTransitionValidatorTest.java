package com.furkan.ordernotification.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.furkan.ordernotification.exception.InvalidOrderStatusTransitionException;
import com.furkan.ordernotification.model.OrderStatus;


class OrderStatusTransitionValidatorTest {

    private final OrderStatusTransitionValidator validator =
        new OrderStatusTransitionValidator();


    @Test
    void createdToPreparingShouldBeValid() {
        assertDoesNotThrow(
            () -> validator.validate(
                OrderStatus.CREATED,
                OrderStatus.PREPARING
            )
        );
    }


    @Test
    void preparingToShippedShouldBeValid() {
        assertDoesNotThrow(
            () -> validator.validate(
                OrderStatus.PREPARING,
                OrderStatus.SHIPPED
            )
        );
    }


    @Test
    void shippedToDeliveredShouldBeValid() {
        assertDoesNotThrow(
            () -> validator.validate(
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED
            )
        );
    }


    @Test
    void createdToCancelledShouldBeValid() {
        assertDoesNotThrow(
            () -> validator.validate(
                OrderStatus.CREATED,
                OrderStatus.CANCELLED
            )
        );
    }


    @Test
    void shippedToCreatedShouldBeInvalid() {
        assertThrows(
            InvalidOrderStatusTransitionException.class,
            () -> validator.validate(
                OrderStatus.SHIPPED,
                OrderStatus.CREATED
            )
        );
    }


    @Test
    void deliveredToPreparingShouldBeInvalid() {
        assertThrows(
            InvalidOrderStatusTransitionException.class,
            () -> validator.validate(
                OrderStatus.DELIVERED,
                OrderStatus.PREPARING
            )
        );
    }


    @Test
    void cancelledToCreatedShouldBeInvalid() {
        assertThrows(
            InvalidOrderStatusTransitionException.class,
            () -> validator.validate(
                OrderStatus.CANCELLED,
                OrderStatus.CREATED
            )
        );
    }
}