package com.nudgebank.paymentbackend.payment.dto;

import com.nudgebank.paymentbackend.payment.domain.PaymentStatus;
import com.nudgebank.paymentbackend.payment.domain.QrPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class PaymentDetailResponse {
    private final String qrId;
    private final Long cardId;
    private final Long marketId;
    private final String marketName;
    private final BigDecimal paymentAmount;
    private final OffsetDateTime requestedAt;
    private final PaymentStatus status;
    private final String menuName;
    private final Integer quantity;

    public static PaymentDetailResponse from(QrPaymentRequest payment) {
        return new PaymentDetailResponse(
                payment.getQrId(),
                payment.getCard().getId(),
                payment.getMarket().getMarketId(),
                payment.getMarket().getMarketName(),
                payment.getPaymentAmount(),
                payment.getRequestedAt(),
                payment.getStatus(),
                payment.getMenuName(),
                payment.getQuantity()
        );
    }
}
