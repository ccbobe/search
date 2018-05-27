package com.bobe.search.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceContextHolder {
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	// 设置数据源名
	public static void setDataSource(String dbType) {
		log.debug("切换到{}数据源", dbType);
		contextHolder.set(dbType);
	}
	
	// 获取数据源名
	public static String getDataSource() {
	
		return (contextHolder.get());
	}
	
	// 清除数据源名
	public static void clearDataSource() {
		contextHolder.remove();
	}
}
