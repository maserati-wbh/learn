package com.taotao.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;

/**
 * 内容分类处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午4:03:49
 * @version 1.0
 */
@RestController
@RequestMapping("/contentcategory")
public class ContentCategoryController {
	
	/** 注入服务层接口代理对象 */
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/** 根据父节点查询所有的子节点 */
	@GetMapping
	public List<Map<String,Object>> selectContentCategoryByParentId(
			@RequestParam(value="id", defaultValue="0")Long parentId){
		try{
			return contentCategoryService.selectContentCategoryByParentId(parentId);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 添加分类 */
	@PostMapping("/save")
	public Long saveContentCategory(ContentCategory contentCategory){
		try{
			return contentCategoryService.saveContentCategory(contentCategory);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 修改分类 */
	@PostMapping("/update")
	public void updateContentCategory(ContentCategory contentCategory){
		try{
			contentCategory.setUpdated(new Date());
			contentCategoryService.updateSelective(contentCategory);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 删除分类 */
	@PostMapping("/delete")
	public void deleteContentCategory(ContentCategory contentCategory){
		try{
			contentCategoryService.deleteContentCategory(contentCategory);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
