package org.pet.payment.resource;

import com.pet.common.TestUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pet.payment.dto.CreatePaymentRequest;
import org.pet.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody @Valid CreatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completePayment(@PathVariable Long id, @TestUser long userId) {
        paymentService.completePayment(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.ok().build();
    }
}
