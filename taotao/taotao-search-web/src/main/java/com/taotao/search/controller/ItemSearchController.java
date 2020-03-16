package com.taotao.search.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.search.service.ItemSearchService;

/**
 * 商品搜索的处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月10日 上午9:57:43
 * @version 1.0
 */
@Controller
public class ItemSearchController {
	
	@Autowired
	private ItemSearchService itemSearchService; 
	
	/** 搜索方法 */
	@GetMapping("/search")
	public String search(@RequestParam(value="q", required=false)String keyword,
				@RequestParam(value="page", defaultValue="1")Integer page,
				Model model) throws Exception{
		
		/** 对搜索关键字进行编码 */
		if (StringUtils.isNoneBlank(keyword)){
			keyword = new String (keyword.getBytes("iso8859-1"), "utf-8");
		}
		/** 添加响应数据 */
		model.addAttribute("query", keyword);
		model.addAttribute("page", page);
		
		Map<String, Object> data = itemSearchService.search(keyword, page, 20);
		model.addAllAttributes(data);
		return "search";
	}
}