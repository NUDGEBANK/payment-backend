package com.nudgebank.paymentbackend.payment.repository;

import com.nudgebank.paymentbackend.payment.domain.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransactionRepository extends JpaRepository<CardTransaction, Long> {
}
