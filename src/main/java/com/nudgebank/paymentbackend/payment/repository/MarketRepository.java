package com.nudgebank.paymentbackend.payment.repository;

import com.nudgebank.paymentbackend.category.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
