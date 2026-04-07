package com.nudgebank.paymentbackend.payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
public class CreateQrPaymentRequest {

    @NotNull
    private Long cardId;

    @NotNull
    private Long marketId;

    @NotNull
    private BigDecimal paymentAmount;

    @NotNull
    private OffsetDateTime requestedAt;

    @NotBlank
    private String menuName;

    @NotNull
    @Min(1)
    private Integer quantity;
}
