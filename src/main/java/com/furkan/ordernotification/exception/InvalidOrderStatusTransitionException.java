package com.furkan.ordernotification.exception;

public class InvalidOrderStatusTransitionException
        extends RuntimeException {

    public InvalidOrderStatusTransitionException(
        String message
    ) {
        super(message);
    }
}