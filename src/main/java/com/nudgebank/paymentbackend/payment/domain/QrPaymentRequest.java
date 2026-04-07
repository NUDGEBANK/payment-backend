package com.nudgebank.paymentbackend.payment.domain;

import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.category.domain.Market;
import com.nudgebank.paymentbackend.common.exception.BusinessException;
import com.nudgebank.paymentbackend.payment.exception.PaymentErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Table(name = "qr_payment_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QrPaymentRequest {

    @Id
    @Column(name = "qr_id", length = 100)
    private String qrId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Column(name = "payment_amount", precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "requested_at")
    private OffsetDateTime requestedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private PaymentStatus status;

    @Column(name = "scanned_at")
    private OffsetDateTime scannedAt;

    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;

    @Column(name = "rejected_at")
    private OffsetDateTime rejectedAt;

    @Column(name = "canceled_at")
    private OffsetDateTime canceledAt;

    @Column(name = "expired_at")
    private OffsetDateTime expiredAt;

    @Column(name = "menu_name", length = 100)
    private String menuName;

    @Column(name = "quantity")
    private Integer quantity;

    public static QrPaymentRequest create(
            String qrId,
            Card card,
            Market market,
            BigDecimal paymentAmount,
            OffsetDateTime requestedAt,
            OffsetDateTime expiresAt,
            String menuName,
            Integer quantity
    ) {
        QrPaymentRequest request = new QrPaymentRequest();
        request.qrId = qrId;
        request.card = card;
        request.market = market;
        request.paymentAmount = paymentAmount;
        request.requestedAt = requestedAt;
        request.menuName = menuName;
        request.quantity = quantity;
        request.status = PaymentStatus.CREATED;
        return request;
    }

    public void markScanned(OffsetDateTime now) {
        validateNotExpired();
        validateCurrentStatus(PaymentStatus.CREATED);
        this.status = PaymentStatus.SCANNED;
        this.scannedAt = now;
    }

    public void markApproved(OffsetDateTime now) {
        validateNotExpired();
        validateCurrentStatus(PaymentStatus.SCANNED);
        this.status = PaymentStatus.APPROVED;
        this.approvedAt = now;
    }

    public void markRejected(OffsetDateTime now) {
        validateNotExpired();
        validateCurrentStatus(PaymentStatus.SCANNED);
        this.status = PaymentStatus.REJECTED;
        this.rejectedAt = now;
    }

    public void markCanceled(OffsetDateTime now) {
        if (this.status != PaymentStatus.CREATED && this.status != PaymentStatus.SCANNED) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }
        this.status = PaymentStatus.CANCELED;
        this.canceledAt = now;
    }

    public void markExpired(OffsetDateTime now) {
        if (this.status != PaymentStatus.CREATED && this.status != PaymentStatus.SCANNED) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }
        this.status = PaymentStatus.EXPIRED;
        this.expiredAt = now;
    }

    private void validateCurrentStatus(PaymentStatus expected) {
        if (this.status != expected) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }
    }

    private void validateNotExpired() {
        if (this.status == PaymentStatus.EXPIRED) {
            throw new BusinessException(PaymentErrorCode.PAYMENT_EXPIRED);
        }
    }
}
