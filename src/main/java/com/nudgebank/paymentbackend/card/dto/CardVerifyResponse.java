package com.nudgebank.paymentbackend.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardVerifyResponse {

    private final Long cardId;
    private final boolean verified;
    private final String message;
}
