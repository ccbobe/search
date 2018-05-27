package com.bobe.search.config;

import org.apache.naming.factory.DataSourceLinkFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig  implements DisposableBean,InitializingBean {
	
	private final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	
	@Override
	public void destroy() throws Exception {
		logger.info("当前数据源信息正在销毁......");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("当前数据源配置信息正在初始化配置......");
	}
	
	@Bean("Master")
	@Primary
	@ConfigurationProperties(prefix = "spring.master.datasource")
	public DataSource MasterDataSource(){//主数据源配置，一般为读写数据源
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("当前数据源信息为{}",dataSource.getClass().getSimpleName());
		return dataSource;
	}
	
	@Bean("Slave")
	@ConfigurationProperties(prefix = "spring.slave.datasource")
	public DataSource SlaveDataSource(){//从数据源配置，一般为只读数据源，减轻主库压力
		logger.info("初始化备库数据源信息{Slave}");
		return DataSourceBuilder.create().build();
	}
	
	@Bean("dynamicRoutingDataSource")
	public DynamicRoutingDataSource dynamicRoutingDataSource(){
		DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
		Map<Object, Object> dataSourceMap = new HashMap<>(4);//设置数据源信息
		dataSourceMap.put(DataSourceKey.Master.name(), MasterDataSource());
		dataSourceMap.put(DataSourceKey.Slave.name(),  SlaveDataSource());
		
		dynamicRoutingDataSource.setDefaultTargetDataSource( MasterDataSource());
		// 将 master 和 slave 数据源作为指定的数据源
		dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
		return dynamicRoutingDataSource;
	}
	
	/**
	 * 配置事务管理器
	 */
	@Bean
	public DataSourceTransactionManager transactionManager(DynamicRoutingDataSource dynamicDataSource) throws Exception {
		return new DataSourceTransactionManager(dynamicDataSource);
	}
	
}
