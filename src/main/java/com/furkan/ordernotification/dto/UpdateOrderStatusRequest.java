package com.furkan.ordernotification.dto;

import com.furkan.ordernotification.model.OrderStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {

    @NotNull(message = "Sipariş durumu zorunludur.")
    private OrderStatus status;


    public UpdateOrderStatusRequest() {
    }


    public OrderStatus getStatus() {
        return status;
    }


    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}