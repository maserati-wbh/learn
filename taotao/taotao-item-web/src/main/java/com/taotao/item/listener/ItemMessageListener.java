package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.ItemService;

import freemarker.template.Template;

/**
 * 商品消息监听器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月13日 上午10:24:53
 * @version 1.0
 */
@Component
public class ItemMessageListener {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	/** 注入商品html文件存储路径 */
	@Value("${itemHtmlFileDir}")
	private String itemHtmlFileDir;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	
	/** 消息监听方法 */
	@JmsListener(destination="item.topic")
	public void onMessage(Map<String,Object> message){
		try{
			System.out.println("taotao-item-web: " + message);
			/** 接收消息，生成该商品的静态html页面 */
			String target = message.get("target").toString();
			Object id = message.get("id");
			/** 判断target */
			if ("delete".equals(target)){
				// 删除商品，删除该商品对应的html页面
				String[] ids = id.toString().split(",");
				for (String itemId : ids){
					File file = new File(itemHtmlFileDir + itemId + ".html");
					if (file.exists() && file.isFile()){
						/** 删除文件 */
						file.delete();
					}
				}
			}else{
				// 添加、修改商品，生成该商品对应的html页面
				/** 根据模版文件，获取模版对象 */
				Template template = freeMarkerConfigurer.getConfiguration()
						.getTemplate("item.ftl");
				Long itemId = (Long)id;
				/** 创建Map集合封装数据 */
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put("item", itemService.findOne(itemId));
				dataModel.put("itemDesc", itemDescService.findOne(itemId));
				
				/** 填充模版文件，生成html文件 */
				template.process(dataModel, new FileWriter(itemHtmlFileDir + id + ".html"));
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
