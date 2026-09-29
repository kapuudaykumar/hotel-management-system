package com.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelmanagement.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}