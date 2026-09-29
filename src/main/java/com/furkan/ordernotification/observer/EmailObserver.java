package com.furkan.ordernotification.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class EmailObserver implements OrderObserver {

    private static final Logger logger =
        LoggerFactory.getLogger(EmailObserver.class);


    @Override
    public void update(
        OrderStatusChangedEvent event
    ) {
        logger.info(
            "E-posta bildirimi -> Sipariş: {}, Müşteri: {}, Durum: {} -> {}",
            event.getOrderId(),
            event.getCustomerEmail(),
            event.getOldStatus(),
            event.getNewStatus()
        );
    }
}