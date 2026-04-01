package com.nudgebank.paymentbackend.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardVerifyRequest {

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String expiredYm;

    @NotBlank
    private String password;

    @NotBlank
    private String cvc;
}
