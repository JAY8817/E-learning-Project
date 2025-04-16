package com.codewithjay.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequestDto {

    private double amount;
    private String userId;
    private String userName;
    private String courseId;

}