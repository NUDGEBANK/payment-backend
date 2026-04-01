package com.nudgebank.paymentbackend.card.exception;

import com.nudgebank.paymentbackend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode implements ErrorCode {

    CARD_VERIFICATION_FAILED("CARD_VERIFICATION_FAILED", "Card verification failed.", HttpStatus.BAD_REQUEST),
    CARD_BLOCKED("CARD_BLOCKED", "Card is blocked.", HttpStatus.FORBIDDEN),
    CARD_EXPIRED("CARD_EXPIRED", "Card is expired.", HttpStatus.GONE),
    CARD_NOT_FOUND("CARD_NOT_FOUND", "Card not found.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
