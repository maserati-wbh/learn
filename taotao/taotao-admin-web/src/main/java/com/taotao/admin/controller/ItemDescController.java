package com.taotao.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemDescService;

/**
 * 商品描述处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月26日 下午7:06:49
 * @version 1.0
 */
@RestController
@RequestMapping("/itemdesc")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	@GetMapping("/{itemId}")
	public ItemDesc getItemDesc(@PathVariable("itemId") Long itemId){
		return itemDescService.findOne(itemId);
	}
}