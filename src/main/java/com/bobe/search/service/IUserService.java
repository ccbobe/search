package com.bobe.search.service;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.domain.po.User;

import java.util.List;

public interface IUserService {

	void saveUser(User user) throws Exception;

	void updateUser(User User) throws Exception;
	
	public List<User> queryUserByPage()throws Exception;
	
}
