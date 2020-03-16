package com.taotao.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.admin.mapper.ItemCatMapper;
import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.ItemCatService;
import com.taotao.admin.service.base.BaseServiceImpl;

/**
 * 商品类目服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月23日 下午6:42:57
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	/**
	 * 根据父节点查询所有的子节点
	 * @param parentId 父节点
	 * @return List
	 */
	public List<Map<String, Object>> selectItemCatByParentId(Long parentId){
		/** 查询数据 */
		List<Map<String, Object>> data = itemCatMapper.selectItemCatByParentId(parentId);
		/**
		 *  [{"id" : 1, "text" : "手机", "state" : "closed"},
	     *   {"id" : 2, "text" : "电脑", "state" : "open"}]
		 */
		for (Map<String, Object> map : data){
			/** 获取state 1: true  0:false */
			boolean state = (boolean)map.get("state");
			map.put("state", state ? "closed" : "open");
		}
		System.out.println(data);
		return data;
		
	}
	
}
