package org.pet.payment.service;

import com.pet.common.client.NotificationClient;
import com.pet.common.client.OrderClient;
import com.pet.common.client.UpdateOrderStatus;
import com.pet.common.client.UserClient;
import com.pet.common.dto.request.WithdrawRequest;
import com.pet.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pet.payment.dto.CreatePaymentRequest;
import org.pet.payment.entity.Payment;
import org.pet.payment.entity.PaymentStatus;
import org.pet.payment.kafka.AnalyticsProducer;
import org.pet.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final NotificationClient notificationClient;
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final UserClient userClient;
    private final AnalyticsProducer analyticsProducer;

    public long createPayment(CreatePaymentRequest request) {
        Payment payment = new Payment();
        payment.setTotal(request.total());
        payment.setOrderId(request.orderId());
        payment.setStatus(PaymentStatus.CREATED);
        payment = paymentRepository.save(payment);
        analyticsProducer.sendPaymentUpdated(payment);
        sendPaymentCreatedNotification(payment);
        return payment.getId();
    }

    public void completePayment(long paymentId, long userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment", paymentId));

        //Check if order connected with user
        withdrawFunds(payment.getOrderId(), userId, payment.getTotal());

        payment.setStatus(PaymentStatus.PAYED);
        analyticsProducer.sendPaymentUpdated(paymentRepository.save(payment));

        //Notify and update order status
        CompletableFuture.runAsync(() -> orderClient.updateOrderStatus(payment.getOrderId(), UpdateOrderStatus.PENDING));
        CompletableFuture.runAsync(() -> notificationClient.sendNotification("Payment completed"));
    }

    public void cancelPayment(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment", paymentId));
        payment.setStatus(PaymentStatus.CANCELLED);
        analyticsProducer.sendPaymentUpdated(paymentRepository.save(payment));

        //Notify and update order status
        CompletableFuture.runAsync(() -> orderClient.cancelOrder(payment.getOrderId()));
        CompletableFuture.runAsync(() -> notificationClient.sendNotification("Payment cancelled"));
    }

    private void sendPaymentCreatedNotification(Payment payment) {
        CompletableFuture.runAsync(() -> notificationClient.sendNotification("Payment created for " + payment.getOrderId()));
    }

    private void withdrawFunds(long orderId, long userId, BigDecimal amount) {
        orderClient.getOrder(orderId, userId);
        userClient.withdrawFromUser(userId, new WithdrawRequest(amount, orderId));
    }
}
