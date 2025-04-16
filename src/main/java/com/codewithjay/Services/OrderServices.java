package com.codewithjay.Services;

import com.codewithjay.Dto.OrderRequestDto;
import com.codewithjay.Entities.Order;
import com.razorpay.RazorpayException;

public interface OrderServices {

    public Order createOrder(OrderRequestDto orderRequestDto) throws RazorpayException;
    public Order updateOrder(String razorpayId, String status );
    public boolean verifyPayment(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature) throws RazorpayException;
}
