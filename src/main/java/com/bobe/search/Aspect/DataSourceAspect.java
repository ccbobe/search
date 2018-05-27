package com.bobe.search.Aspect;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Order(10)
@Component
public class DataSourceAspect {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Pointcut("@annotation(com.bobe.search.common.DataSourceType)")
	public void dsType(){}
	
	@Before("dsType()")
	public void changeDs(JoinPoint joinPoint){
	
		//返回目标对象
		Object target = joinPoint.getTarget();
		String targetName = target.getClass().getName();
		//返回当前连接点签名
		String methodName = joinPoint.getSignature().getName();
		//获得参数列表
		Object[] arguments = joinPoint.getArgs();
		logger.debug("当前参数{}，{}，{}",targetName,methodName,arguments);
		
		Method[] methods =null;
		
		try {
			Class targetClass = Class.forName(targetName);
			methods = targetClass.getMethods();
			logger.info("当前类中存在方法名有{}",methods.toString());
			for (Method m : methods) {
				//判断方法上是否有指定类型注解
				boolean doCheck  = m.isAnnotationPresent(DataSourceType.class);
				logger.info("=======:{}",m.getName());
				if (doCheck){
					if (methodName.equals(m.getName())){
						DataSourceType dataSourceType = m.getAnnotation(DataSourceType.class);
						String data = dataSourceType.value();
						if (data!= null && !"".equals(data)) {//切换数据源
							//在方法内部中可以手动切换数据源
							DynamicDataSourceContextHolder.setDataSource(data);
							logger.info("当前切换到数据源名称为：{}"+data);
							break;
						}
					
					}else {
						//logger.warn("切换数据源出现异常，可能原因书数据源名称为空或者其他信息");
					}
				}
			}
			
		} catch (Exception e) {
			logger.warn("切换数据源出现异常信息:{}",e.getMessage());
		}
	}
	
	@After("dsType()")
	public void restoreDs(JoinPoint joinPoint){
		DynamicDataSourceContextHolder.clearDataSource();
		logger.info("数据源已发生切换");
	}
}
