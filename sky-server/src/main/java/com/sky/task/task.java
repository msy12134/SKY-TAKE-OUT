package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j


public class task {
    @Scheduled(cron = "0/1 * * * * ?")
    public void task() {
        log.info("task started now");
    }
}
