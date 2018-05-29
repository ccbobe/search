package com.bobe.search.service.impl;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.config.DataSourceKey;
import com.bobe.search.config.DynamicDataSourceContextHolder;
import com.bobe.search.dao.IUserDao;
import com.bobe.search.domain.po.User;
import com.bobe.search.service.IUserService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserDao IUserDao;

	@DataSourceType("Slave")
	@CachePut(value = "user",key = "#user.userName")
	@Async("SimpleExecutor")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(User user) throws Exception {
		System.out.println("=====>");
		logger.info("saveUser"+new Date());
		IUserDao.saveUser(user);
		
	//	DynamicDataSourceContextHolder.setDataSource(DataSourceKey.Slave.name());
		IUserDao.saveUser(user);
	//	int sum =  1/0;
	//	DynamicDataSourceContextHolder.clearDataSource();
	}
	@DataSourceType("Slave")
	@Async("MasterExecutor")
	@Override
	@CachePut(value = "user",key = "#user.userName")
	public void updateUser(User user) throws Exception {
		logger.info("当前执行消息为:");
	}
	//@Cacheable(value = "user",key = "queryUserByPage")
	@DataSourceType("Slave")
	@Override
	public List<User> queryUserByPage() throws Exception {
		PageHelper.startPage(0, 20);
		return IUserDao.queryUserByPage();
	}
}
