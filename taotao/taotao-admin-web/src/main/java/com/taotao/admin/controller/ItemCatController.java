package com.taotao.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;

/**
 * 商品类目处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月23日 下午6:45:38
 * @version 1.0
 */
@RestController
@RequestMapping("/itemcat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	/**
	 * 分页查询商品类目
	 * http://localhost:9091/itemcat/1?rows=5
	 */
	@GetMapping("/{pageNum}")
	public List<ItemCat> selectItemCatByPage(
			@PathVariable("pageNum") Integer pageNum,
			@RequestParam(value="rows", defaultValue="20")Integer rows){
		return itemCatService.findByPage(pageNum, rows);
	}
	
	/** 根据父节点查询所有的子节点 */
	@GetMapping
	public List<Map<String, Object>> selectItemCatByParentId(
			@RequestParam(value="id", defaultValue="0")Long parentId){
		return itemCatService.selectItemCatByParentId(parentId);
		
	}
}