package com.codewithjay.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="orders_mindup")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String pmtStatus;

    private LocalDate createdDate;

    @Column(nullable = false)
    private String razorpayOrderId;

    @Column(nullable = false)
    private String courseId;

    @Column(nullable = false)
    private String userId;




}
