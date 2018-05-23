package com.bobe.search.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class DateTask {
	private final Logger logger = LoggerFactory.getLogger(DateTask.class);
	@Scheduled(cron = "0 0/1 * * * ?")
	@Async // 异步标记可以将当前方法异步在异步线程池中执行，可以不放在自己的线程池执行
	public void showDate(){
		logger.info("当前时间为{"+new SimpleDateFormat("yyyy-MM-dd HH:mm:dd:sss").format(new Date())+"}");
	}
}
