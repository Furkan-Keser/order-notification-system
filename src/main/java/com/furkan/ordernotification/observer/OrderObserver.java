package com.furkan.ordernotification.observer;

public interface OrderObserver {

    void update(OrderStatusChangedEvent event);
}