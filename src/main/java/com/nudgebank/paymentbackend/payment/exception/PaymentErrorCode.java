package com.nudgebank.paymentbackend.payment.exception;

import com.nudgebank.paymentbackend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    PAYMENT_NOT_FOUND("PAYMENT_NOT_FOUND", "QR payment request not found.",HttpStatus.NOT_FOUND),
    MARKET_NOT_FOUND("MARKET_NOT_FOUND", "Market not found.", HttpStatus.NOT_FOUND),
    INVALID_PAYMENT_STATUS("INVALID_PAYMENT_STATUS", "Invalid payment status transition.", HttpStatus.CONFLICT),
    PAYMENT_EXPIRED("PAYMENT_EXPIRED", "QR payment has expired.", HttpStatus.GONE),
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "Insufficient account balance.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
