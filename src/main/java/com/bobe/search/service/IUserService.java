package com.bobe.search.service;

import com.bobe.search.domain.po.User;

public interface IUserService {
	
	void saveUser(User user) throws Exception;
	
	void updateUser(User User) throws Exception;
	
	
}
