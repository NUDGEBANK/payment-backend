package com.nudgebank.paymentbackend.history.exception;

import com.nudgebank.paymentbackend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum HistoryErrorCode implements ErrorCode {
    INVALID_HISTORY_PERIOD("INVALID_HISTORY_PERIOD", "Invalid history period.", HttpStatus.BAD_REQUEST),
    INVALID_HISTORY_DATE_RANGE("INVALID_HISTORY_DATE_RANGE", "Invalid history date range.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
