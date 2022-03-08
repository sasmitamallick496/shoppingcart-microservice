package com.sasmita.musings.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sasmita.musings.paymentservice.entity.PaymentInfo;

public interface PaymentRepository extends JpaRepository<PaymentInfo, Long>{

}
