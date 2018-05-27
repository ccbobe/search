package com.bobe.search.Aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;

@Aspect
@Order(9)
@Component
public class RequestAspect {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Pointcut("execution(* com.bobe.search.controller..*.*(..))")
	public void requestLog(){
	
	}
	
	@Before("requestLog()")
	public void requestStart(){
		logger.info("当前请求开始。。。。。。");
	}
	@After("requestLog()")
	public void requestEnd(){
		logger.info("当前请求结束。。。。。。");
	}
}
