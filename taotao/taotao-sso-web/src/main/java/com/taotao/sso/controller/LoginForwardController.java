package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 跳转到登录页面
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:52:01
 * @version 1.0
 */
@Controller
public class LoginForwardController {
	
	@GetMapping("/login")
	public String forward(@RequestParam(value="redirectURL", 
			defaultValue="http://www.taotao.com")String redirectURL, Model model){
		
		/** 添加响应数据 */
		model.addAttribute("redirectURL", redirectURL);
		return "login";
	}
}
