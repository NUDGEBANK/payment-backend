package com.nudgebank.paymentbackend.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CardBalanceResponse {

    private final Long cardId;
    private final Long accountId;
    private final BigDecimal balance;

}
