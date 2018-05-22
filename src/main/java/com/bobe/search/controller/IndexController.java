package com.bobe.search.controller;

import com.bobe.search.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="/api")
public class IndexController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/index")
	@ResponseBody
	public String index(){
		
		try {
			userService.saveUser(null);
			userService.updateUser(null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "hello world";
	}
}
