package com.furkan.ordernotification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furkan.ordernotification.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}