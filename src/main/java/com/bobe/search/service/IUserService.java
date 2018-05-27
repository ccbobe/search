package com.bobe.search.service;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.domain.po.User;

public interface IUserService {
	@DataSourceType
	void saveUser(User user) throws Exception;

	void updateUser(User User) throws Exception;
	
	
}
