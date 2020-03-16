package com.taotao.search.listener;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.search.pojo.SolrItem;
import com.taotao.search.service.ItemSearchService;

/**
 * 商品消息监听器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月11日 上午9:12:15
 * @version 1.0
 */
public class ItemMessageListener implements
		SessionAwareMessageListener<MapMessage> {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(MapMessage message, Session session)
			throws JMSException {
		System.out.println("taotao-search-web: ItemMessageListener");
		try{
			/** 获取消息内容 */
			String target = message.getString("target");
			Object id = message.getObject("id");
			System.out.println("target:" + target);
			System.out.println("id:" + id);
			/** 判断 */
			if ("delete".equals(target)){ // 删除 id: 1,2,3
				String ids = (String)id;
				itemSearchService.delete(ids.split(","));
			}else{ // 添加与修改
				Long itemId = (Long)id; // 商品id
				/** 根据商品id查询商品 */
				Item item = itemService.findOne(itemId);
				/** 把商品Item转化成SolrItem */
				SolrItem solrItem = new SolrItem();
				solrItem.setId(item.getId());
				solrItem.setImage(item.getImage());
				solrItem.setPrice(item.getPrice());
				solrItem.setSellPoint(item.getSellPoint());
				solrItem.setStatus(item.getStatus());
				solrItem.setTitle(item.getTitle());
				List<SolrItem> solrItems = new ArrayList<>();
				solrItems.add(solrItem);
				/** 更新索引库 */
				itemSearchService.saveOrUpdate(solrItems);
			}
			/** 提交消息事务 */
			session.commit();
		}catch(Exception ex){
			/** 回滚消息事务 */
			session.rollback();
			throw new RuntimeException(ex);
		}
	}
}