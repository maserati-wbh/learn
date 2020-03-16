package com.taotao.admin.service;

import java.util.List;
import java.util.Map;

import com.taotao.admin.pojo.ItemCat;
import com.taotao.admin.service.base.BaseService;

/**
 * 商品类目服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月23日 下午6:42:19
 * @version 1.0
 */
public interface ItemCatService extends BaseService<ItemCat>{
	/**
	 * 根据父节点查询所有的子节点
	 * @param parentId 父节点
	 * @return List
	 */
	List<Map<String, Object>> selectItemCatByParentId(Long parentId);
	
}
