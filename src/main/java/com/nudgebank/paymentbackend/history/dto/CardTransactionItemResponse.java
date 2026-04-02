package com.nudgebank.paymentbackend.history.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class CardTransactionItemResponse {
    private final Long transactionId;
    private final String qrId;
    private final Long marketId;
    private final String marketName;
    private final Long categoryId;
    private final String categoryName;
    private final BigDecimal amount;
    private final OffsetDateTime transactionDatetime;
    private final String menuName;
    private final Integer quantity;
}
