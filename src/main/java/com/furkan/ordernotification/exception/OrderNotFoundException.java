package com.furkan.ordernotification.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Sipariş bulunamadı: " + id);
    }
}