package com.nudgebank.paymentbackend.card.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

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
}
