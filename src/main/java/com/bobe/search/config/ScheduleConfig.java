package com.bobe.search.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer,InitializingBean,DisposableBean {
	
	private final Logger logger = LoggerFactory.getLogger(SchedulingConfigurer.class);
	
	@NotNull
	private ExecutorService executorService;
	//核心线程数
	private final Integer POOL_SIZE = 5;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(executorService);
	}
	
	@Override
	public void destroy() throws Exception {//核心调度线程池销毁
		logger.info("调度器线程池正在销毁中....");
		if (executorService != null){
			executorService.shutdownNow();
		}
		
		if(!executorService.isTerminated()){
			executorService.shutdownNow();
		}
		
		if (!executorService.isShutdown()){
			executorService.shutdownNow();
		}
		
		executorService = null;
		
		logger.info("当前调度器线程池销毁完毕！");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化调度器线程池......");
		try {
			initThreadPool(POOL_SIZE);
		} catch (Exception e) {
			logger.warn("当前线程池初始化异常{}",e.getMessage());
		}
		logger.info("初始化调度器核心线程池完毕！");
	}
	
	public void initThreadPool( Integer PoolSize){
		executorService = Executors.newScheduledThreadPool(PoolSize);
	}
	
}
