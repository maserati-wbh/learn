package com.taotao.search.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;

/**
 * 商品搜索服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月8日 下午5:14:49
 * @version 1.0
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
	
	@Autowired
	private SolrClient solrClient;
	/** 注入索引库 */
	@Value("${solr.defaultCollection}")
	private String collection;
	
	/**
	 * 批量添加或修改索引
	 * @param solrItems
	 */
	public void saveOrUpdate(List<SolrItem> solrItems){
		try{
			/** 添加或修改索引库 */
			UpdateResponse  updateResponse = solrClient.addBeans(collection, solrItems);
			/** 判断状态码 */
			if (updateResponse.getStatus() == 0){
				/** 提交事务 */
				solrClient.commit(collection);
			}else{
				/** 回滚事务 */
				solrClient.rollback(collection);
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 批量删除索引
	 * @param ids 多个商品id
	 */
	public void delete(String[] ids){
		try{
			/** 删除索引库 */
			UpdateResponse  updateResponse = solrClient.deleteById(collection, Arrays.asList(ids));
			/** 判断状态码 */
			if (updateResponse.getStatus() == 0){
				/** 提交事务 */
				solrClient.commit(collection);
			}else{
				/** 回滚事务 */
				solrClient.rollback(collection);
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 全文检索的方法
	 * @param keyword 关键字
	 * @param page 当前页码
	 * @param rows 每页显示的记录数
	 * @return Map集合
	 */
	public Map<String, Object> search(String keyword, Integer page, int rows){
		try{
			/** 创建查询对象SolrQuery */
			SolrQuery solrQuery = new SolrQuery();
			/** 判断关键字 */
			if (StringUtils.isBlank(keyword)){
				keyword ="*";
			}
			/** 设置查询字符串 */
			solrQuery.setQuery("title:" + keyword + " AND status:1");
			/** 设置开始分页记录数 limit的第一个? */
			solrQuery.setStart((page - 1) * rows);
			/** 设置每页显示的记录数 */
			solrQuery.setRows(rows);
			
			if (!"*".equals(keyword)){
				/** 设置高亮显示 */
				solrQuery.setHighlight(true);
				/** 设置高亮字段 */
				solrQuery.addHighlightField("title");
				/** 设置文本截断 */
				solrQuery.setHighlightFragsize(60);
				/** 设置高亮格式器前缀 */
				solrQuery.setHighlightSimplePre("<font color='red'>");
				/** 设置高亮格式器后缀 */
				solrQuery.setHighlightSimplePost("</font>");
			}
			
			/** 检索得到查询响应对象 */
			QueryResponse queryResponse = solrClient.query(collection, solrQuery);
			
			/** 定义Map集合 */
			Map<String, Object> data = new HashMap<>();
			
			/** 判断状态码 */
			if (queryResponse.getStatus() == 0){
				
				/** 获取命中的总记录数 */
				long count = queryResponse.getResults().getNumFound();
				/** 计算总页数 */
				long totalPage = ((count - 1) / rows) + 1;
				/** ${totalPage}: 总页数*/
				data.put("totalPage", totalPage);
				
				/** 获取检索到的数据 */
				List<SolrItem> solrItems = queryResponse.getBeans(SolrItem.class);
				
				/** 判断是否高亮 */
				if (solrQuery.getHighlight()){
					/** 获取高亮数据 */
					Map<String, Map<String, List<String>>> hMaps = queryResponse.getHighlighting();
					for (SolrItem solrItem : solrItems){
						/** 获取标题高亮内容 */
						String title = hMaps.get(String.valueOf(solrItem.getId()))
										.get("title").get(0);
						solrItem.setTitle(title);
					}
				}
				//  ${itemList}
				data.put("itemList", solrItems);
			}
			return data;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}