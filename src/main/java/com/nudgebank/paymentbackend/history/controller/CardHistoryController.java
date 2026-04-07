package com.nudgebank.paymentbackend.history.controller;

import com.nudgebank.paymentbackend.history.domain.HistoryPeriodType;
import com.nudgebank.paymentbackend.history.dto.CardHistorySummaryResponse;
import com.nudgebank.paymentbackend.history.dto.CardTransactionHistoryResponse;
import com.nudgebank.paymentbackend.history.service.CardHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/history")
public class CardHistoryController {

    private final CardHistoryService cardHistoryService;

    @GetMapping("/summary")
    public ResponseEntity<CardHistorySummaryResponse> getSummary(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardHistoryService.getSummary(cardId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<CardTransactionHistoryResponse> getTransactions(
            @PathVariable Long cardId,
            @RequestParam HistoryPeriodType periodType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(cardHistoryService.getTransactions(cardId, periodType, startDate, endDate));
    }
}
