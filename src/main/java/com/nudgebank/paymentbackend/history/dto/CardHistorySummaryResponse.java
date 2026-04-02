package com.nudgebank.paymentbackend.history.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CardHistorySummaryResponse {
    private final Long cardId;
    private final BigDecimal currentBalance;
    private final BigDecimal currentMonthSpending;
    private final BigDecimal previousMonthSpending;
    private final BigDecimal changeRatePercent;
}
