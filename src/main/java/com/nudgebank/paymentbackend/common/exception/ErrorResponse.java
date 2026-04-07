package com.nudgebank.paymentbackend.common.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        int status,
        OffsetDateTime timestamp,
        List<FieldError> fieldErrors
) {

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                OffsetDateTime.now(),
                List.of()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, List<FieldError> fieldErrors) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                OffsetDateTime.now(),
                fieldErrors
        );
    }

    public record FieldError(
            String field,
            String reason
    ) {
    }
}
