package com.codewithjay.Reposetories;

import com.codewithjay.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {


    Order findByRazorpayOrderId(String razorPayId);


}