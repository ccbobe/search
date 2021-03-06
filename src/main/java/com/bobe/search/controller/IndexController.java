package com.bobe.search.controller;

import com.bobe.search.common.DataSourceType;
import com.bobe.search.common.ResponseJson;
import com.bobe.search.domain.dto.UserDto;
import com.bobe.search.domain.po.User;
import com.bobe.search.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value ="/api")
public class IndexController {
	
	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	IUserService userService;
	
	@PostMapping("/index")
	@ResponseBody
	public ResponseJson index(@RequestBody UserDto userDto, BindingResult bindingResult){
		/*
		* 参数验证只需要使用注解@Validated 标记即可验证
		* BindingResult 通过BindingResult 获取验证返回信息。
		* */
		ResponseJson json = new ResponseJson();
		if (bindingResult.hasErrors()){
			json.setCode("1000");
			json.setMsg("参数验证不通过");
			json.setData(bindingResult.getGlobalError());
			return json;
		}
		User user = new User();
		BeanUtils.copyProperties(userDto,user);
		try {
			userService.saveUser(user);
			json.setCode("1002");
			json.setMsg("完成创建工作");
		
		} catch (Exception e) {
			json.setCode("9999");
			json.setMsg("系统异常，请稍后重试");
			logger.warn("当前系统出现异常，异常信息为{}",e.getMessage());
			return json;
		}
		return json;
	}
	
	@PostMapping("/queryUsers")
	@ResponseBody
	public ResponseJson queryUserByPage(){
		ResponseJson json = new ResponseJson();
		List<User> users = null;
		try {
		    users = userService.queryUserByPage();
		} catch (Exception e) {
			logger.error("查询异常{}",e.getMessage());
			json.setCode("9999");
			return json;
		}
		json.setCode("1000");
		json.setMsg("查询成功");
		json.setData(users);
		return json;
	}
}
