package com.bobe.search.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;

@Configuration
public class MybaitsConfig {
	
	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;
	
	
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis")
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
		sqlSessionFactoryBean.setDataSource(dynamicRoutingDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		//分页相关设置
		try {
			//设置xml扫描路径
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybaits/mapping/*.xml"));
			return sqlSessionFactoryBean;
		} catch (Exception e) {
			throw new RuntimeException("sqlSessionFactory init fail",e);
		}
	}
}
