package com.codewithjay.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerifyDto {

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

}
