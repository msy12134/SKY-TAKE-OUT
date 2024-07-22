package com.sky.task;


import com.sky.entity.Orders;
import com.sky.mapper.ordermapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ordertask {

    @Autowired
    private ordermapper ordermapper;
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutorder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = ordermapper.getbystatusandordertime(Orders.PENDING_PAYMENT, time);
        if(ordersList !=null && ordersList.size()>0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                ordermapper.update(orders);
            }
        }

    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processdeliveryorder(){
        log.info("定时处理处于派送中的订单", LocalDateTime.now());
        LocalDateTime time= LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList=ordermapper.getbystatusandordertime(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList !=null && ordersList.size()>0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                ordermapper.update(orders);
            }
        }
    }


}
