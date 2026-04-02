package com.nudgebank.paymentbackend.payment.dto;

import com.nudgebank.paymentbackend.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class PaymentStatusResponse {
    private final String qrId;
    private final PaymentStatus status;
    private final OffsetDateTime changedAt;
    private final String message;
}
