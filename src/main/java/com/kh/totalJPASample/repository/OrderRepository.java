package com.kh.totalJPASample.repository;


import com.kh.totalJPASample.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
