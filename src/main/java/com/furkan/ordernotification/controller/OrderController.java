package com.furkan.ordernotification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkan.ordernotification.dto.CreateOrderRequest;
import com.furkan.ordernotification.dto.UpdateOrderStatusRequest;
import com.furkan.ordernotification.model.Order;
import com.furkan.ordernotification.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/orders")
@Tag(
    name = "Orders",
    description = "Sipariş yönetimi işlemleri"
)
public class OrderController {

    private final OrderService orderService;


    public OrderController(
        OrderService orderService
    ) {
        this.orderService = orderService;
    }


    @PostMapping
    @Operation(
        summary = "Yeni sipariş oluştur",
        description = "Yeni bir sipariş oluşturur ve CREATED durumunda kaydeder."
    )
    public ResponseEntity<Order> createOrder(
        @Valid @RequestBody CreateOrderRequest request
    ) {
        Order order = new Order(
            request.getCustomerName(),
            request.getCustomerEmail(),
            request.getTotalAmount()
        );

        Order createdOrder =
            orderService.createOrder(order);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdOrder);
    }


    @GetMapping
    @Operation(
        summary = "Tüm siparişleri getir",
        description = "Sistemde kayıtlı bütün siparişleri listeler."
    )
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(
            orderService.getAllOrders()
        );
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Siparişi ID ile getir",
        description = "Belirtilen ID'ye sahip siparişi getirir."
    )
    public ResponseEntity<Order> getOrderById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(
            orderService.getOrderById(id)
        );
    }


    @PatchMapping("/{id}/status")
    @Operation(
        summary = "Sipariş durumunu güncelle",
        description =
            "Sipariş durumunu günceller ve Observer'lara event yayınlar."
    )
    public ResponseEntity<Order> updateOrderStatus(
        @PathVariable Long id,
        @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(
            orderService.updateOrderStatus(
                id,
                request.getStatus()
            )
        );
    }
}