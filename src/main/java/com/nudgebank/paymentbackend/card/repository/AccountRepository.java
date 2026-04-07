package com.nudgebank.paymentbackend.card.repository;

import com.nudgebank.paymentbackend.card.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
