package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 跳转到注册页面
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:52:01
 * @version 1.0
 */
@Controller
public class RegisterForwardController {
	
	@GetMapping("/register")
	public String forward(){
		
		return "register";
	}
}
