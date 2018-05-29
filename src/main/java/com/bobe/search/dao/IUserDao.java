package com.bobe.search.dao;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.domain.po.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
	
	void saveUser(User user) throws Exception;
	
	void updateUser(User User) throws Exception;
	
	List<User> queryUserByPage ()throws Exception;
}
