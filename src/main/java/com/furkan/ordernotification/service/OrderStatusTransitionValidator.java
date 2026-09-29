package com.furkan.ordernotification.service;

import org.springframework.stereotype.Component;

import com.furkan.ordernotification.exception.InvalidOrderStatusTransitionException;
import com.furkan.ordernotification.model.OrderStatus;


@Component
public class OrderStatusTransitionValidator {

    public void validate(
        OrderStatus currentStatus,
        OrderStatus newStatus
    ) {
        boolean validTransition = switch (currentStatus) {

            case CREATED ->
                newStatus == OrderStatus.PREPARING
                || newStatus == OrderStatus.CANCELLED;

            case PREPARING ->
                newStatus == OrderStatus.SHIPPED
                || newStatus == OrderStatus.CANCELLED;

            case SHIPPED ->
                newStatus == OrderStatus.DELIVERED;

            case DELIVERED, CANCELLED ->
                false;
        };

        if (!validTransition) {
            throw new InvalidOrderStatusTransitionException(
                "Geçersiz sipariş durum geçişi: "
                + currentStatus
                + " -> "
                + newStatus
            );
        }
    }
}