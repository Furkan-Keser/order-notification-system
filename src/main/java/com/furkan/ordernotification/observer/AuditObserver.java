package com.furkan.ordernotification.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AuditObserver implements OrderObserver {

    private static final Logger logger =
        LoggerFactory.getLogger(AuditObserver.class);


    @Override
    public void update(
        OrderStatusChangedEvent event
    ) {
        logger.info(
            "AUDIT -> Sipariş: {}, {} -> {}, Tarih: {}",
            event.getOrderId(),
            event.getOldStatus(),
            event.getNewStatus(),
            event.getChangedAt()
        );
    }
}