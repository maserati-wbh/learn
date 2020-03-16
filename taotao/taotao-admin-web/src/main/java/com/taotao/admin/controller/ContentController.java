package com.taotao.admin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.admin.pojo.Content;
import com.taotao.admin.service.ContentService;
import com.taotao.common.vo.DataGridResult;

/**
 * 内容处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午5:52:15
 * @version 1.0
 */
@RestController
@RequestMapping("/content")
public class ContentController {
	
	/** 注入业务层接口代理对象 */
	@Autowired
	private ContentService contentService; 
	
	/** 根据分类分页查询内容 */
	@GetMapping
	public DataGridResult selectContentByPage(
				@RequestParam("page") Integer page,
				@RequestParam("rows") Integer rows,
				@RequestParam(value="categoryId", defaultValue="0") Long categoryId){
		try{
			return contentService.selectContentByPage(page, rows, categoryId);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 添加内容 */
	@PostMapping("/save")
	public void saveContent(Content content){
		try{
			content.setCreated(new Date());
			content.setUpdated(content.getCreated());
			contentService.insertSelective(content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 修改内容 */
	@PostMapping("/update")
	public void updateContent(Content content){
		try{
			content.setUpdated(new Date());
			contentService.updateSelective(content);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 批量删除内容 */
	@PostMapping("/delete")
	public void deleteContent(@RequestParam("ids")String ids){
		try{
			contentService.deleteAll("id", ids.split(","));
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
