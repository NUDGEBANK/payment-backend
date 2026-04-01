package com.nudgebank.paymentbackend.card.service;

import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.card.domain.CardStatus;
import com.nudgebank.paymentbackend.card.dto.CardBalanceResponse;
import com.nudgebank.paymentbackend.card.dto.CardVerifyRequest;
import com.nudgebank.paymentbackend.card.dto.CardVerifyResponse;
import com.nudgebank.paymentbackend.card.exception.CardErrorCode;
import com.nudgebank.paymentbackend.card.repository.CardRepository;
import com.nudgebank.paymentbackend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public CardVerifyResponse verifyCard(CardVerifyRequest request) {
        Card card = cardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new BusinessException(CardErrorCode.CARD_VERIFICATION_FAILED));

        if (!card.matchesExpiredYm(request.getExpiredYm())) {
            throw new BusinessException(CardErrorCode.CARD_VERIFICATION_FAILED);
        }

        if (!card.matchesPassword(request.getPassword())) {
            throw new BusinessException(CardErrorCode.CARD_VERIFICATION_FAILED);
        }

        if (!card.isActive()) {
            throw new BusinessException(CardErrorCode.CARD_BLOCKED);
        }

        if (card.isExpired()) {
            throw new BusinessException(CardErrorCode.CARD_EXPIRED);
        }

        return new CardVerifyResponse(card.getId(), true, "Card verification succeeded.");
    }

    public CardBalanceResponse getCardBalance(Long cardId) {
        Card card = cardRepository.findWithAccountById(cardId)
                .orElseThrow(() -> new BusinessException(CardErrorCode.CARD_NOT_FOUND));

        validateCardUsable(card);

        return new CardBalanceResponse(
                card.getId(),
                card.getAccount().getAccountId(),
                card.getAccount().getBalance()
        );
    }

    private void validateCardUsable(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new BusinessException(CardErrorCode.CARD_BLOCKED);
        }

        if (card.isExpired()) {
            throw new BusinessException(CardErrorCode.CARD_EXPIRED);
        }
    }
}
