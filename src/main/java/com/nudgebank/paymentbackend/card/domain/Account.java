package com.nudgebank.paymentbackend.card.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "account_name", length = 100)
    private String accountName;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "balance", precision = 15, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "account_created_at")
    private OffsetDateTime createdAt;

    @Column(name = "account_type", length = 20)
    private String accountType;
}
