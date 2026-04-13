package com.nudgebank.paymentbackend.common.client;

import com.nudgebank.paymentbackend.payment.dto.AutoRepaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bank-client", url = "${bank.server.url}")
public interface BankClient {
    @PostMapping("/api/auto-repayment/execute")
    void notifyPayment(@RequestBody AutoRepaymentRequest signal);


}
