package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.taotao.admin.service.ContentService;

/**
 * 跳转到门户网站的首页
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午2:53:46
 * @version 1.0
 */
@Controller
public class IndexController {
	
	/** 注入内容管理接口服务的代理对象 */
	@Autowired
	private ContentService contentService;
	
	/**  跳转到门户网站的首页 */
	@GetMapping("/index")
	public String index(ModelMap modelMap){
		
		/** 查询大广告数据 */
		String bigAdData = contentService.findBigAdData();
		/** 添加大广告数据到Request作用域 */
		modelMap.addAttribute("bigAdData", bigAdData);
		return "index";
	}
}
