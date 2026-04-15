package com.nudgebank.paymentbackend.card.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "opened_at")
    private OffsetDateTime openedAt;

    public void withdraw(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        this.balance = this.balance.subtract(amount);
    }

}
