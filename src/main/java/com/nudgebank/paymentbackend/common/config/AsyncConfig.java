package com.nudgebank.paymentbackend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    @Bean(name = "paymentNotifyExecutor")
    public Executor paymentNotifyExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);   // 기본 유지 쓰레드
        executor.setMaxPoolSize(10);  // 최대 확장 쓰레드
        executor.setQueueCapacity(100); // 대기 큐
        executor.setThreadNamePrefix("BankNotify-");
        executor.initialize();
        return executor;
    }
}
