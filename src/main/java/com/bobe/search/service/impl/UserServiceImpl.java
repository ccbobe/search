package com.bobe.search.service.impl;

import com.bobe.search.domain.po.User;
import com.bobe.search.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String TAG = "userServiceImpl";
	@Async("SimpleExecutor")
	@Override
	public void saveUser(User user) throws Exception {
		System.out.println("=====>");
		logger.info("saveUser"+new Date());
	}
	
	@Async("MasterExecutor")
	@Override
	public void updateUser(User User) throws Exception {
		logger.info("当前执行消息为:");
	}
}
