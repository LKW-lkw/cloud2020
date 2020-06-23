package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_ok(Integer id){
        return "线程池:"+Thread.currentThread().getName()+"  paymentId:"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })
    public String paymentInfo_timeout(Integer id){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int age= 10/1;
        return "线程池:"+Thread.currentThread().getName()+" 耗时2秒";
    }

    public String paymentInfo(Integer id){
        return "线程池:"+Thread.currentThread().getName()+" 8001系统繁忙或者运行报错，请稍后再试，id: "+id;
    }

    @HystrixCommand(fallbackMethod = "paymentFallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String payment(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("id不能为负数！");
        }
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+uuid;
    }

    public String paymentFallback(@PathVariable("id") Integer id){
        return "id真的不能为负数啊！";
    }
}
