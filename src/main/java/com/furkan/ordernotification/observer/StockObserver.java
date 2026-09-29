package com.furkan.ordernotification.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.furkan.ordernotification.model.OrderStatus;


@Component
public class StockObserver implements OrderObserver {

    private static final Logger logger =
        LoggerFactory.getLogger(StockObserver.class);


    @Override
    public void update(
        OrderStatusChangedEvent event
    ) {
        if (event.getNewStatus() == OrderStatus.CANCELLED) {
            logger.info(
                "STOCK -> Sipariş {} iptal edildi. Stok iade işlemi başlatıldı.",
                event.getOrderId()
            );

            return;
        }

        if (event.getOldStatus() == OrderStatus.CREATED
            && event.getNewStatus() == OrderStatus.PREPARING) {

            logger.info(
                "STOCK -> Sipariş {} hazırlanıyor. Stok rezervasyonu yapıldı.",
                event.getOrderId()
            );
        }
    }
}