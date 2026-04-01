package com.nudgebank.paymentbackend.card.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Getter
@Table(name = "card")
public class Card {

    private static final DateTimeFormatter EXPIRED_YM_FORMATTER = DateTimeFormatter.ofPattern("yy/MM");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "card_number", length = 30, nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "expired_ym", length = 5, nullable = false)
    private String expiredYm; // 예: 27/03

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private CardStatus status;

    @Column(name = "cvc", length = 3, nullable = false)
    private String cvc;

    public boolean matchesExpiredYm(String inputExpiredYm) {
        return Objects.equals(this.expiredYm, inputExpiredYm);
    }

    public boolean matchesPassword(String inputPassword) {
        return Objects.equals(this.password, inputPassword);
    }

    public boolean matchesCvc(String inputCvc) {
        return Objects.equals(this.cvc, inputCvc);
    }

    public boolean isActive() {
        return this.status == CardStatus.ACTIVE;
    }

    public boolean isExpired() {
        YearMonth now = YearMonth.now();
        YearMonth cardExpired = YearMonth.parse(this.expiredYm, EXPIRED_YM_FORMATTER);
        return cardExpired.isBefore(now);
    }

}
