package com.bobe.search.controller;

import com.bobe.search.domain.dto.UserDto;
import com.bobe.search.domain.po.User;
import com.bobe.search.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value ="/api")
public class IndexController {
	
	@Autowired
	IUserService userService;
	
	@PostMapping("/index")
	@ResponseBody
	public String index(@RequestBody UserDto userDto){
		/*User user = new User();
		user.setUserName("bobe");
		user.setEmail("bobe@qq.com");*/
		User user = new User();
		BeanUtils.copyProperties(userDto,user);
		try {
			userService.saveUser(user);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "hello world";
	}
}
