package com.taotao.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;

/**
 * SearchIndexTest
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月10日 上午9:01:21
 * @version 1.0
 */
public class ItemIndexTest {
	
	private ItemService itemService;
	private ItemSearchService itemSearchService; 
	
	@Before
	public void before(){
		/** 获取Spring容器 */
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("taotao-search-web-servlet.xml");
		this.itemService = ac.getBean(ItemService.class);
		this.itemSearchService = ac.getBean(ItemSearchService.class);
	}

	@Test
	public void createIndex(){
		/** 定义当前页码 */
		int page = 1;
		/** 定义每页显示的数据 */
		int rows = 500;
		do{
			System.out.println("开始查询第【"+ page +"】页的数据");
			/** 分页查询商品数据 */
			List<Item> items = itemService.findByPage(page, rows);
			if (items != null && items.size() > 0){
				/** 把List<Item> 转化成 List<SolrItem> */
				List<SolrItem> solrItems = new ArrayList<>();
				for (Item item : items){
					/** 把Item转化成SolrItem */
					SolrItem solrItem = new SolrItem();
					solrItem.setId(item.getId());
					solrItem.setImage(item.getImage());
					solrItem.setPrice(item.getPrice());
					solrItem.setSellPoint(item.getSellPoint());
					solrItem.setStatus(item.getStatus());
					solrItem.setTitle(item.getTitle());
					solrItems.add(solrItem);
				}
				/** 把商品数据通过Solrj创建索引 */
				itemSearchService.saveOrUpdate(solrItems);
				
				page++;
				rows = items.size();
			}else{
				rows = 0;
			}
			System.out.println("结束查询第【"+ page +"】页的数据");
			
		}while(rows == 500);
	}
}