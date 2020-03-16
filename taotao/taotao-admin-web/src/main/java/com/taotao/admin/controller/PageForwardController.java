package com.taotao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 通用页面跳转的处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午2:51:30
 * @version 1.0
 */
@Controller
public class PageForwardController {
	
	/** 通用页面跳转方法 */
	@GetMapping("/page/{viewName}")
	public String forward(@PathVariable("viewName")String viewName){
		return viewName;
	}
}
