package com.nudgebank.paymentbackend.card.repository;

import com.nudgebank.paymentbackend.card.domain.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumber(String cardNumber);

    @EntityGraph(attributePaths = "account")
    Optional<Card> findWithAccountById(Long accountId);

    @EntityGraph(attributePaths = "account")
    Optional<Card> findWithAccountByCardNumber(String cardNumber);

}
