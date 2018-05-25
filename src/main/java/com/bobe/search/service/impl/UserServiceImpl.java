package com.bobe.search.service.impl;

import com.bobe.search.dao.IUserDao;
import com.bobe.search.domain.po.User;
import com.bobe.search.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private IUserDao IUserDao;
	
	@Cacheable(value = "user",key = "#user.userName")
	@Async("SimpleExecutor")
	@Override
	public void saveUser(User user) throws Exception {
		System.out.println("=====>");
		logger.info("saveUser"+new Date());
		IUserDao.saveUser(user);
	}
	
	@Async("MasterExecutor")
	@Override
	@Cacheable(value = "user",key = "#user.userName")
	public void updateUser(User user) throws Exception {
		logger.info("当前执行消息为:");
	}
}
