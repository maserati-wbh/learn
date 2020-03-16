package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.ItemService;

/**
 * 商品详情处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月13日 上午9:01:52
 * @version 1.0
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	/**
	 * 跳转到商品详情页面
	 * http://item.taotao.com/10129834642.html
	 */
	@GetMapping("/{itemId}")
	public String getItem(@PathVariable("itemId")Long itemId, Model model){
		
		/** 根据商品id查询商品信息 */
		model.addAttribute("item", itemService.findOne(itemId));
		/** 根据商品id查询商品描述信息 */
		model.addAttribute("itemDesc", itemDescService.findOne(itemId));
		return "item";
	}
}
