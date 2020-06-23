package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements OrderService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "----PaymentFallbackService fall paymentInfo_ok";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "----PaymentFallbackService fall paymentInfo_timeout";
    }
}
