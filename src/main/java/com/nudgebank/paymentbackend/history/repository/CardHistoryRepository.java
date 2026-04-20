package com.nudgebank.paymentbackend.history.repository;

import com.nudgebank.paymentbackend.history.dto.CardTransactionItemResponse;
import com.nudgebank.paymentbackend.payment.domain.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public interface CardHistoryRepository extends JpaRepository<CardTransaction, Long> {

    @Query("""
            select coalesce(sum(ct.amount), 0)
            from CardTransaction ct
            where ct.card.id = :cardId
              and ct.transactionDatetime >= :start
              and ct.transactionDatetime < :end
              and (
                  ct.menuName is null
                  or ct.menuName not in ('넛지 대출', '자기계발 대출')
              )
            """)
    BigDecimal sumAmountByCardIdAndPeriod(Long cardId, OffsetDateTime start, OffsetDateTime end);

    @Query("""
            select new com.nudgebank.paymentbackend.history.dto.CardTransactionItemResponse(
                ct.transactionId,
                ct.qrId,
                m.marketId,
                m.marketName,
                c.categoryId,
                c.categoryName,
                ct.amount,
                ct.transactionDatetime,
                ct.menuName,
                ct.quantity
            )
            from CardTransaction ct
            join ct.market m
            join ct.category c
            where ct.card.id = :cardId
              and ct.transactionDatetime >= :start
              and ct.transactionDatetime < :end
            order by ct.transactionDatetime desc, ct.transactionId desc
            """)
    List<CardTransactionItemResponse> findTransactionItemsByCardIdAndPeriod(
            Long cardId,
            OffsetDateTime start,
            OffsetDateTime end
    );
}
