package com.nudgebank.paymentbackend.card.service;

import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.card.domain.CardStatus;
import com.nudgebank.paymentbackend.card.dto.CardBalanceResponse;
import com.nudgebank.paymentbackend.card.dto.CardVerifyRequest;
import com.nudgebank.paymentbackend.card.dto.CardVerifyResponse;
import com.nudgebank.paymentbackend.card.repository.CardRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));

        if (!card.matchesExpiredYm(request.getExpiredYm())) {
            throw new IllegalArgumentException("카드 유효기간이 일치하지 않습니다.");
        }

        if (!card.matchesPassword(request.getPassword())) {
            throw new IllegalArgumentException("카드 비밀번호가 일치하지 않습니다.");
        }

        if (!card.isActive()) {
            throw new IllegalStateException("사용할 수 없는 카드 상태입니다. status=" + card.getStatus());
        }

        if (card.isExpired()) {
            throw new IllegalStateException("유효기간이 만료된 카드입니다.");
        }

        return new CardVerifyResponse(card.getId(), true, "카드 인증에 성공했습니다.");
    }

    public CardBalanceResponse getCardBalance(Long cardId) {
        Card card = cardRepository.findWithAccountById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));

        validateCardUsable(card);

        return new CardBalanceResponse(
                card.getId(),
                card.getAccount().getAccountId(),
                card.getAccount().getBalance()
        );
    }

    private void validateCardUsable(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("사용할 수 없는 카드 상태입니다. status=" + card.getStatus());
        }

        if (card.isExpired()) {
            throw new IllegalStateException("유효기간이 만료된 카드입니다.");
        }
    }

}
