package com.CodeLab.Code_Execution_Service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "jdoodleExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);   // Adjust based on expected load
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(50);

//        executor.setCorePoolSize(15);
//        executor.setMaxPoolSize(30);  // Match number of test cases
//        executor.setQueueCapacity(100);

        executor.setCorePoolSize(50);     // 50 threads always available
        executor.setMaxPoolSize(50);      // max = 50 (same as number of test cases)
        executor.setQueueCapacity(100);   // just in case
        executor.setThreadNamePrefix("JDoodleAsync-");
        executor.initialize();
        return executor;
    }
}