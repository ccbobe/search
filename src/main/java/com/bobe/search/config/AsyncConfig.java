package com.bobe.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean("MasterExecutor")
	public ThreadPoolTaskExecutor masterExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(20);
		taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.setThreadGroupName("Task-");
		taskExecutor.setThreadNamePrefix("Async-");
		taskExecutor.setBeanName("MasterExecutor");
		return taskExecutor;
	}
	
	@Bean("SimpleExecutor")
	public ThreadPoolTaskExecutor simpleExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(2);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.setThreadGroupName("Task-");
		taskExecutor.setThreadNamePrefix("Simple-");
		taskExecutor.setBeanName("SimpleExecutor");
		return taskExecutor;
	}
}
