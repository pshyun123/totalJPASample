package com.kh.totalJPASample.repository;


import com.kh.totalJPASample.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
