package com.bobe.search.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;
import java.util.Properties;

@Configuration
public class MybaitsConfig {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis")
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
		//完成数据源动态切换注解切面编程，请注意：切面编程必须在事务之前发生切换，否则容易出现错误
		//事务切面order值默认为50
		sqlSessionFactoryBean.setDataSource(dynamicRoutingDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		logger.info("当前为SqlSessionFactoryBean初始化相关配置信息");
		//分页相关设置/////
		
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{ pageHelper()});
		
		///////////////////
		try {
			//设置xml扫描路径
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybaits/mapping/*.xml"));
			return sqlSessionFactoryBean;
		} catch (Exception e) {
			throw new RuntimeException("sqlSessionFactory init fail",e);
		}
	}
	
	
	@Bean("pageHelper")
	public PageHelper pageHelper() {
		//mybaits插件集成配置
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect","mysql");
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
