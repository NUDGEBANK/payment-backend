package com.nudgebank.paymentbackend.history.dto;

import com.nudgebank.paymentbackend.history.domain.HistoryPeriodType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CardTransactionHistoryResponse {
    private final Long cardId;
    private final HistoryPeriodType periodType;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int count;
    private final List<CardTransactionItemResponse> transactions;
}
