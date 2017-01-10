package com.baeldung.spring.session;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@EnableScheduling
public class ThreadConfig extends AsyncConfigurerSupport implements SchedulingConfigurer{

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public Executor executor() {
        return makeExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        return makeExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(schedulingExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor schedulingExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    private Executor makeExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.initialize();

        return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
    }


}
