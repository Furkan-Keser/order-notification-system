package com.furkan.ordernotification.observer;

import java.time.LocalDateTime;

import com.furkan.ordernotification.model.OrderStatus;


public class OrderStatusChangedEvent {

    private final Long orderId;
    private final String customerEmail;
    private final OrderStatus oldStatus;
    private final OrderStatus newStatus;
    private final LocalDateTime changedAt;


    public OrderStatusChangedEvent(
        Long orderId,
        String customerEmail,
        OrderStatus oldStatus,
        OrderStatus newStatus
    ) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = LocalDateTime.now();
    }


    public Long getOrderId() {
        return orderId;
    }


    public String getCustomerEmail() {
        return customerEmail;
    }


    public OrderStatus getOldStatus() {
        return oldStatus;
    }


    public OrderStatus getNewStatus() {
        return newStatus;
    }


    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}