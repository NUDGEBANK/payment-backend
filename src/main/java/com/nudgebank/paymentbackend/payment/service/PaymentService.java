package com.nudgebank.paymentbackend.payment.service;

import com.nudgebank.paymentbackend.card.domain.Account;
import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.card.domain.CardStatus;
import com.nudgebank.paymentbackend.card.exception.CardErrorCode;
import com.nudgebank.paymentbackend.card.repository.CardRepository;
import com.nudgebank.paymentbackend.category.domain.Market;
import com.nudgebank.paymentbackend.common.exception.BusinessException;
import com.nudgebank.paymentbackend.payment.domain.CardTransaction;
import com.nudgebank.paymentbackend.payment.domain.QrPaymentRequest;
import com.nudgebank.paymentbackend.payment.dto.CreateQrPaymentRequest;
import com.nudgebank.paymentbackend.payment.dto.CreateQrPaymentResponse;
import com.nudgebank.paymentbackend.payment.dto.PaymentDetailResponse;
import com.nudgebank.paymentbackend.payment.dto.PaymentStatusResponse;
import com.nudgebank.paymentbackend.payment.exception.PaymentErrorCode;
import com.nudgebank.paymentbackend.payment.repository.CardTransactionRepository;
import com.nudgebank.paymentbackend.payment.repository.MarketRepository;
import com.nudgebank.paymentbackend.payment.repository.QrPaymentRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private static final long EXPIRE_MINUTES = 3L;

    private final QrPaymentRequestRepository qrPaymentRequestRepository;
    private final CardTransactionRepository cardTransactionRepository;
    private final CardRepository cardRepository;
    private final MarketRepository marketRepository;

    @Transactional
    public CreateQrPaymentResponse createQrPayment(CreateQrPaymentRequest request) {
        Card card = cardRepository.findWithAccountById(request.getCardId())
                .orElseThrow(() -> new BusinessException(CardErrorCode.CARD_NOT_FOUND));

        Market market = marketRepository.findById(request.getMarketId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.MARKET_NOT_FOUND));

        validateCardUsable(card);

        String qrId = UUID.randomUUID().toString();
        OffsetDateTime expiresAt = request.getRequestedAt().plusMinutes(EXPIRE_MINUTES);

        QrPaymentRequest payment = QrPaymentRequest.create(
                qrId,
                card,
                market,
                request.getPaymentAmount(),
                request.getRequestedAt(),
                expiresAt,
                request.getMenuName(),
                request.getQuantity()
        );

        qrPaymentRequestRepository.save(payment);
        return new CreateQrPaymentResponse(payment.getQrId(), payment.getStatus());
    }

    public PaymentDetailResponse getPayment(String qrId) {
        return PaymentDetailResponse.from(getPaymentWithDetails(qrId));
    }

    @Transactional
    public PaymentDetailResponse scanPayment(String qrId) {
        QrPaymentRequest payment = getPaymentWithDetails(qrId);
        payment.markScanned(OffsetDateTime.now());
        return PaymentDetailResponse.from(payment);
    }

    @Transactional
    public PaymentStatusResponse approvePayment(String qrId) {
        OffsetDateTime now = OffsetDateTime.now();
        QrPaymentRequest payment = getPaymentWithDetails(qrId);

        validateCardUsable(payment.getCard());
        validateBalance(payment.getCard().getAccount(), payment);

        payment.markApproved(now);
        payment.getCard().getAccount().withdraw(payment.getPaymentAmount());
        cardTransactionRepository.save(CardTransaction.from(payment, now));

        return new PaymentStatusResponse(payment.getQrId(), payment.getStatus(), now, "Payment approved.");
    }

    @Transactional
    public PaymentStatusResponse rejectPayment(String qrId) {
        OffsetDateTime now = OffsetDateTime.now();
        QrPaymentRequest payment = getPaymentWithDetails(qrId);
        payment.markRejected(now);
        return new PaymentStatusResponse(payment.getQrId(), payment.getStatus(), now, "Payment rejected.");
    }

    @Transactional
    public PaymentStatusResponse cancelPayment(String qrId) {
        OffsetDateTime now = OffsetDateTime.now();
        QrPaymentRequest payment = getPaymentWithDetails(qrId);
        payment.markCanceled(now);
        return new PaymentStatusResponse(payment.getQrId(), payment.getStatus(), now, "Payment canceled.");
    }

    @Transactional
    public PaymentStatusResponse expirePayment(String qrId) {
        OffsetDateTime now = OffsetDateTime.now();
        QrPaymentRequest payment = getPaymentWithDetails(qrId);
        payment.markExpired(now);
        return new PaymentStatusResponse(payment.getQrId(), payment.getStatus(), now, "Payment expired.");
    }

    private QrPaymentRequest getPaymentWithDetails(String qrId) {
        return qrPaymentRequestRepository.findWithCardAndMarketByQrId(qrId)
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    private void validateCardUsable(Card card) {
        if (!card.isActive() || card.getStatus() != CardStatus.ACTIVE) {
            throw new BusinessException(CardErrorCode.CARD_BLOCKED);
        }
        if (card.isExpired()) {
            throw new BusinessException(CardErrorCode.CARD_EXPIRED);
        }
    }

    private void validateBalance(Account account, QrPaymentRequest payment) {
        if (account.getBalance().compareTo(payment.getPaymentAmount()) < 0) {
            throw new BusinessException(PaymentErrorCode.INSUFFICIENT_BALANCE);
        }
    }
}
