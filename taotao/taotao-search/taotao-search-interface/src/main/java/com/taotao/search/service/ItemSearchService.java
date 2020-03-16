package com.taotao.search.service;

import java.util.List;
import java.util.Map;

import com.taotao.search.pojo.SolrItem;

/**
 * 商品搜索服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月8日 下午5:14:04
 * @version 1.0
 */
public interface ItemSearchService {
	
	/**
	 * 批量添加或修改索引
	 * @param solrItems
	 */
	void saveOrUpdate(List<SolrItem> solrItems);
	/**
	 * 全文检索的方法
	 * @param keyword 关键字
	 * @param page 当前页码
	 * @param rows 每页显示的记录数
	 * @return Map集合
	 */
	Map<String, Object> search(String keyword, Integer page, int rows);
	/**
	 * 批量删除索引
	 * @param ids 多个商品id
	 */
	void delete(String[] ids);

}