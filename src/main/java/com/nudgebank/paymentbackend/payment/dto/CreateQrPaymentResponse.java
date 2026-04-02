package com.nudgebank.paymentbackend.payment.dto;

import com.nudgebank.paymentbackend.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQrPaymentResponse {
    private final String qrId;
    private final PaymentStatus status;
}
