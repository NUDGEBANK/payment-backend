package com.nudgebank.paymentbackend.history.service;

import com.nudgebank.paymentbackend.card.domain.Card;
import com.nudgebank.paymentbackend.card.exception.CardErrorCode;
import com.nudgebank.paymentbackend.card.repository.CardRepository;
import com.nudgebank.paymentbackend.common.exception.BusinessException;
import com.nudgebank.paymentbackend.history.domain.HistoryPeriodType;
import com.nudgebank.paymentbackend.history.dto.CardHistorySummaryResponse;
import com.nudgebank.paymentbackend.history.dto.CardTransactionHistoryResponse;
import com.nudgebank.paymentbackend.history.dto.CardTransactionItemResponse;
import com.nudgebank.paymentbackend.history.exception.HistoryErrorCode;
import com.nudgebank.paymentbackend.history.repository.CardHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardHistoryService {

    private static final ZoneId SERVICE_ZONE = ZoneId.of("Asia/Seoul");

    private final CardRepository cardRepository;
    private final CardHistoryRepository cardHistoryRepository;

    public CardHistorySummaryResponse getSummary(Long cardId) {
        Card card = cardRepository.findWithAccountById(cardId)
                .orElseThrow(() -> new BusinessException(CardErrorCode.CARD_NOT_FOUND));

        YearMonth currentMonth = YearMonth.now(SERVICE_ZONE);
        YearMonth previousMonth = currentMonth.minusMonths(1);

        OffsetDateTime currentMonthStart = atStartOfMonth(currentMonth);
        OffsetDateTime nextMonthStart = atStartOfMonth(currentMonth.plusMonths(1));
        OffsetDateTime previousMonthStart = atStartOfMonth(previousMonth);

        BigDecimal currentMonthSpending = cardHistoryRepository.sumAmountByCardIdAndPeriod(
                cardId, currentMonthStart, nextMonthStart
        );
        BigDecimal previousMonthSpending = cardHistoryRepository.sumAmountByCardIdAndPeriod(
                cardId, previousMonthStart, currentMonthStart
        );

        BigDecimal changeRatePercent = calculateChangeRate(currentMonthSpending, previousMonthSpending);

        return new CardHistorySummaryResponse(
                cardId,
                card.getAccount().getBalance(),
                currentMonthSpending,
                previousMonthSpending,
                changeRatePercent
        );
    }

    public CardTransactionHistoryResponse getTransactions(
            Long cardId,
            HistoryPeriodType periodType,
            LocalDate startDate,
            LocalDate endDate
    ) {
        cardRepository.findById(cardId)
                .orElseThrow(() -> new BusinessException(CardErrorCode.CARD_NOT_FOUND));

        DateRange dateRange = resolveDateRange(periodType, startDate, endDate);

        OffsetDateTime start = dateRange.startDate().atStartOfDay(SERVICE_ZONE).toOffsetDateTime();
        OffsetDateTime endExclusive = dateRange.endDate().plusDays(1).atStartOfDay(SERVICE_ZONE).toOffsetDateTime();

        List<CardTransactionItemResponse> items = cardHistoryRepository.findTransactionItemsByCardIdAndPeriod(
                cardId, start, endExclusive
        );

        return new CardTransactionHistoryResponse(
                cardId,
                periodType,
                dateRange.startDate(),
                dateRange.endDate(),
                items.size(),
                items
        );
    }

    private BigDecimal calculateChangeRate(BigDecimal current, BigDecimal previous) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : null;
        }

        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private OffsetDateTime atStartOfMonth(YearMonth yearMonth) {
        ZonedDateTime zonedDateTime = yearMonth.atDay(1).atStartOfDay(SERVICE_ZONE);
        return zonedDateTime.toOffsetDateTime();
    }

    private DateRange resolveDateRange(HistoryPeriodType periodType, LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now(SERVICE_ZONE);

        return switch (periodType) {
            case ONE_MONTH -> new DateRange(today.minusMonths(1), today);
            case THREE_MONTHS -> new DateRange(today.minusMonths(3), today);
            case SIX_MONTHS -> new DateRange(today.minusMonths(6), today);
            case ONE_YEAR -> new DateRange(today.minusYears(1), today);
            case CUSTOM -> {
                if (startDate == null || endDate == null) {
                    throw new BusinessException(HistoryErrorCode.INVALID_HISTORY_DATE_RANGE);
                }
                if (startDate.isAfter(endDate)) {
                    throw new BusinessException(HistoryErrorCode.INVALID_HISTORY_DATE_RANGE);
                }
                yield new DateRange(startDate, endDate);
            }
        };
    }

    private record DateRange(LocalDate startDate, LocalDate endDate) {
    }
}
