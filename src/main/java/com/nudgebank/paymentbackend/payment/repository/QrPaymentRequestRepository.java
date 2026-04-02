package com.nudgebank.paymentbackend.payment.repository;

import com.nudgebank.paymentbackend.payment.domain.QrPaymentRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrPaymentRequestRepository extends JpaRepository<QrPaymentRequest, String> {

    @EntityGraph(attributePaths = {"card", "card.account", "market", "market.category"})
    Optional<QrPaymentRequest> findWithCardAndMarketByQrId(String qrId);
}
