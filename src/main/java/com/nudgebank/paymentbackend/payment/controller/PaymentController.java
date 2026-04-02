package com.nudgebank.paymentbackend.payment.controller;

import com.nudgebank.paymentbackend.payment.dto.CreateQrPaymentRequest;
import com.nudgebank.paymentbackend.payment.dto.CreateQrPaymentResponse;
import com.nudgebank.paymentbackend.payment.dto.PaymentDetailResponse;
import com.nudgebank.paymentbackend.payment.dto.PaymentStatusResponse;
import com.nudgebank.paymentbackend.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/qr")
    public ResponseEntity<CreateQrPaymentResponse> createQrPayment(
            @Valid @RequestBody CreateQrPaymentRequest request
    ) {
        return ResponseEntity.ok(paymentService.createQrPayment(request));
    }

    @GetMapping("/qr/{qrId}")
    public ResponseEntity<PaymentDetailResponse> getPayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.getPayment(qrId));
    }

    @PostMapping("/qr/{qrId}/scan")
    public ResponseEntity<PaymentDetailResponse> scanPayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.scanPayment(qrId));
    }

    @PostMapping("/qr/{qrId}/approve")
    public ResponseEntity<PaymentStatusResponse> approvePayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.approvePayment(qrId));
    }

    @PostMapping("/qr/{qrId}/reject")
    public ResponseEntity<PaymentStatusResponse> rejectPayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.rejectPayment(qrId));
    }

    @PostMapping("/qr/{qrId}/cancel")
    public ResponseEntity<PaymentStatusResponse> cancelPayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.cancelPayment(qrId));
    }

    @PostMapping("/qr/{qrId}/expire")
    public ResponseEntity<PaymentStatusResponse> expirePayment(@PathVariable String qrId) {
        return ResponseEntity.ok(paymentService.expirePayment(qrId));
    }

}
