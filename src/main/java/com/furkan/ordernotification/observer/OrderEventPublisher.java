package com.furkan.ordernotification.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class OrderEventPublisher {

    private final List<OrderObserver> observers;


    public OrderEventPublisher(
        List<OrderObserver> observers
    ) {
        this.observers =
            new ArrayList<>(observers);
    }


    public void subscribe(
        OrderObserver observer
    ) {
        observers.add(observer);
    }


    public void unsubscribe(
        OrderObserver observer
    ) {
        observers.remove(observer);
    }


    public void notifyObservers(
        OrderStatusChangedEvent event
    ) {
        for (OrderObserver observer : observers) {
            observer.update(event);
        }
    }
}