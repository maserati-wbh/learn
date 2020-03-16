package com.taotao.admin.controller;

import java.net.URLDecoder;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.common.vo.DataGridResult;

/**
 * 商品的处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午6:10:20
 * @version 1.0
 */
@RestController
@RequestMapping("/item")
public class ItemController {
	
	/** 注入服务层接口的代理对象 */
	@Autowired
	private ItemService itemService;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/** 添加商品 */
	@PostMapping("/save")
	public void saveItem(Item item, @RequestParam("desc")String desc){
		try{
			Long id = itemService.saveItem(item, desc);
			sendItemTopicMessage("save", id);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 修改商品 */
	@PostMapping("/update")
	public void updaateItem(Item item, @RequestParam("desc")String desc){
		try{
			itemService.updateItem(item, desc);
			sendItemTopicMessage("update", item.getId());
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 分页查询商品 */
	@GetMapping
	public DataGridResult selectItemByPage(Item item,
			@RequestParam("page")Integer page,
			@RequestParam("rows")Integer rows){
		try{
			if (item != null && StringUtils.isNoneBlank(item.getTitle())){
				item.setTitle(URLDecoder.decode(item.getTitle(), "utf-8"));
			}
			return itemService.selectItemByPage(item, page, rows);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送消息到消息中间件
	 * @param target 操作
	 * @param id 商品id (单个商品id、多个商品id)
	 */
	private void sendItemTopicMessage(final String target, final Object id){
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				/** 创建消息对象 */
				MapMessage mapMessage = new ActiveMQMapMessage();
				mapMessage.setString("target", target);
				mapMessage.setObject("id", id);
				return mapMessage;
			}
		});
	}
}