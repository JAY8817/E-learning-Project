package com.codewithjay.Controller;

import com.codewithjay.Dto.OrderRequestDto;
import com.codewithjay.Dto.PaymentVerifyDto;
import com.codewithjay.Entities.Order;
import com.codewithjay.Entities.OrderDetail;
import com.codewithjay.Reposetories.OrderRepo;
import com.codewithjay.ServicesImpl.OrderServiceImpl;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepo repo;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto dto) throws RazorpayException {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerifyDto paymentVerifyDto) throws RazorpayException {

        boolean b = orderService.verifyPayment(paymentVerifyDto.getRazorpayPaymentId(), paymentVerifyDto.getRazorpayOrderId(), paymentVerifyDto.getRazorpaySignature());
        if (b) {
            OrderDetail orderDetail = new OrderDetail();
            Order order = repo.findByRazorpayOrderId(paymentVerifyDto.getRazorpayOrderId());
            orderDetail.setCourseId(order.getCourseId());
            orderDetail.setOrderStatus(true);
            orderDetail.setUserId(order.getUserId());
            orderDetail.setOrderPaymentStatus(true);
            orderDetail.setEmail(order.getUserName());
//            orderCreatedNotification(new OrderDetail());
            return ResponseEntity.ok("Order Verified");

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment Not Verified");
        }

    }


//    @Autowired
//    private StreamBridge streamBridge;
//
//    private void orderCreatedNotification(OrderDetail orderDetail) {
//        //logic to send notification to notification service
//
//        boolean send = streamBridge.send("orderCreatedEvent-out-0", orderDetail);
//        if (send) {
//            System.out.println("Order Success Event is successfully send to notification service");
//        } else {
//            System.out.println("Event fail:");
//        }
//    }
}