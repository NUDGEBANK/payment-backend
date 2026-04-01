package com.nudgebank.paymentbackend.card.controller;

import com.nudgebank.paymentbackend.card.dto.CardBalanceResponse;
import com.nudgebank.paymentbackend.card.dto.CardVerifyRequest;
import com.nudgebank.paymentbackend.card.dto.CardVerifyResponse;
import com.nudgebank.paymentbackend.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;

    @PostMapping("/verify")
    public ResponseEntity<CardVerifyResponse> verifyCard(@Valid @RequestBody CardVerifyRequest request) {
        return ResponseEntity.ok(cardService.verifyCard(request));
    }

    @GetMapping("/{cardId}/balance")
    public ResponseEntity<CardBalanceResponse> getCardBalance(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCardBalance(cardId));
    }
}
