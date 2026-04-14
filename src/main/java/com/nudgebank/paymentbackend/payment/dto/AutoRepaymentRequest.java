package com.nudgebank.paymentbackend.payment.dto;

import com.nudgebank.paymentbackend.payment.domain.CardTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AutoRepaymentRequest {
    Long memberId;
    Long cardTransaction;
}
