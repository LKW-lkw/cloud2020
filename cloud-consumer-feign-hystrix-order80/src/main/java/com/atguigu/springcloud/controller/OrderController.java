package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/consumer/payment/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "orderInfo",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    //@HystrixCommand
    public String paymentInfo_timeout(@PathVariable("id") Integer id){
        return orderService.paymentInfo_timeout(id);
    }

    @GetMapping(value = "/consumer/payment/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        return orderService.paymentInfo_ok(id);
    }

    public String orderInfo(Integer id){
       return "80消费端，对方支付系统繁忙稍后再试！";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试！";
    }
}
