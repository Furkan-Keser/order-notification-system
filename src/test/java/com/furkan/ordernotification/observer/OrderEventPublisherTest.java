package com.furkan.ordernotification.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.furkan.ordernotification.model.OrderStatus;


class OrderEventPublisherTest {

    @Test
    void observerShouldReceivePublishedEvent() {

        RecordingObserver observer =
            new RecordingObserver();

        OrderEventPublisher publisher =
            new OrderEventPublisher(
                List.of(observer)
            );

        OrderStatusChangedEvent event =
            new OrderStatusChangedEvent(
                1L,
                "test@example.com",
                OrderStatus.CREATED,
                OrderStatus.PREPARING
            );

        publisher.notifyObservers(event);

        assertEquals(
            1,
            observer.getNotificationCount()
        );

        assertEquals(
            event,
            observer.getLastEvent()
        );
    }


    private static class RecordingObserver
            implements OrderObserver {

        private int notificationCount;
        private OrderStatusChangedEvent lastEvent;


        @Override
        public void update(
            OrderStatusChangedEvent event
        ) {
            notificationCount++;
            lastEvent = event;
        }


        public int getNotificationCount() {
            return notificationCount;
        }


        public OrderStatusChangedEvent getLastEvent() {
            return lastEvent;
        }
    }
}