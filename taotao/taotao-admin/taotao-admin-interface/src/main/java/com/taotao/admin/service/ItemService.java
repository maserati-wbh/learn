package com.taotao.admin.service;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.base.BaseService;
import com.taotao.common.vo.DataGridResult;

/**
 * 商品业务处理接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午6:03:36
 * @version 1.0
 */
public interface ItemService extends BaseService<Item> {
	
	/** 添加商品 */
	Long saveItem(Item item, String desc);
	
	/** 修改商品 */
	void updateItem(Item item, String desc);
	
	/** 分页查询商品 */
	DataGridResult selectItemByPage(Item item, Integer page, Integer rows);
	
}
