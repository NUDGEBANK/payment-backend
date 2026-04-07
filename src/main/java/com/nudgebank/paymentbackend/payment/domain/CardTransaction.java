package com.nudgebank.paymentbackend.payment.domain;

import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.category.domain.Market;
import com.nudgebank.paymentbackend.category.domain.MarketCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Table(name = "card_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private MarketCategory category;

    @Column(name = "qr_id", nullable = false, length = 100)
    private String qrId;

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_datetime", nullable = false)
    private OffsetDateTime transactionDatetime;

    @Column(name = "menu_name", length = 100)
    private String menuName;

    @Column(name = "quantity")
    private Integer quantity;

    public static CardTransaction from(QrPaymentRequest paymentRequest, OffsetDateTime approvedAt) {
        CardTransaction transaction = new CardTransaction();
        transaction.card = paymentRequest.getCard();
        transaction.market = paymentRequest.getMarket();
        transaction.category = paymentRequest.getMarket().getCategory();
        transaction.qrId = paymentRequest.getQrId();
        transaction.amount = paymentRequest.getPaymentAmount();
        transaction.transactionDatetime = approvedAt;
        transaction.menuName = paymentRequest.getMenuName();
        transaction.quantity = paymentRequest.getQuantity();
        return transaction;
    }
}
