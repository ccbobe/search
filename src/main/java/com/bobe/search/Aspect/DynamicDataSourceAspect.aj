package com.bobe.search.Aspect;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.reflect.Method;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Aspect
@Order(1) //在事务之前发生切面
@Component
public aspect DynamicDataSourceAspect {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut("@annotation(com.bobe.search.common.DataSourceType)")
	public void dataType(){
		logger.info("当前数据源发生切换");
	}
	
	@Before("dataType()")
	public void changeDataSource() throws Throwable {
			logger.warn("当前切换数据源发生异常");
		
	}
	
	@After("dataType()")
	public void clearDataSourceType(){
		//清除数据源切换，并将设置默认数据源信息
		DynamicDataSourceContextHolder.clearDataSource();
		logger.info("切面之后数据源信息发生变化");
	}
}
