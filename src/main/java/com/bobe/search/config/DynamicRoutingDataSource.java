package com.bobe.search.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSource();
	}
	
}
