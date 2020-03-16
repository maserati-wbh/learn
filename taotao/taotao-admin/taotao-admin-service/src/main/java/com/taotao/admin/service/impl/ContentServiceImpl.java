package com.taotao.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ContentMapper;
import com.taotao.admin.pojo.Content;
import com.taotao.admin.service.ContentService;
import com.taotao.admin.service.base.BaseServiceImpl;
import com.taotao.common.redis.RedisService;
import com.taotao.common.vo.DataGridResult;

/**
 * 内容业务处理接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午3:47:33
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class ContentServiceImpl extends BaseServiceImpl<Content> implements
		ContentService {
	/** 注入数据访问接口代理对象 */
	@Autowired
	private ContentMapper contentMapper;
	/** 定义ObjectMapper操作json */
	private ObjectMapper objectMapper = new ObjectMapper();
	/** 注入RedisService */
	@Autowired
	private RedisService redisService;
	/** 定义大广告存放在Redis中key */
	private static final String REDIS_INDEX_BIGAD = "redis_index_bigad";
	
	
	/**
	 * 根据分类分页查询内容
	 * @param page 当前页码
	 * @param rows 每页显示记录数
	 * @param categoryId 分类id
	 * @return easyui的datagrid组件需要的数据
	 */
	public DataGridResult selectContentByPage(Integer page, Integer rows,
			final Long categoryId){
		try{
			/** 开启分页 */
			PageInfo<Content> pageInfo = PageHelper.startPage(page, rows)
					.doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					Content content = new Content();
					content.setCategoryId(categoryId);
					contentMapper.select(content);
				}
			});
			/** 定义DataGridResult对象封装easyui的datagrid组件需要的数据 */
			DataGridResult dataGridResult = new DataGridResult();
			dataGridResult.setTotal(pageInfo.getTotal());
			dataGridResult.setRows(pageInfo.getList());
			return dataGridResult;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 查询大广告数据
	 * @return JSON格式的字符串[{}]
	 */
	public String findBigAdData(){
		try{
			
			String bigAdJsonData = "";
			try{
				/** 先从Redis数据库中取缓存的数据，如果有就直接返回 */
				bigAdJsonData = redisService.get(REDIS_INDEX_BIGAD);
				/** 判断bigAdData */
				if (StringUtils.isNoneBlank(bigAdJsonData)){
					System.out.println("Redis数据库中取缓存的数据: " + bigAdJsonData);
					return bigAdJsonData;
				}
			}catch(Exception ex){}
			
			/** 根据大广告分类，分页查询数据 */
			@SuppressWarnings("unchecked")
			List<Content> contents = (List<Content>)selectContentByPage(1, 6, 8L).getRows();
			/**
			 * [ {
				"alt": "",
				"height": 240,
				"heightB": 240,
				"href": "http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE",
				"src": "http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg",
				"srcB": "http://image.taotao.com/images/2015/03/03/2015030304360302109345.jpg",
				"width": 670,
				"widthB": 550
		        }]
			 */
			List<Map<String, Object>> bigAdData = new ArrayList<>();
			for (Content content: contents){
				Map<String, Object> map = new HashMap<>();
				map.put("alt", content.getTitle());
				map.put("height", 240);
				map.put("heightB", 240);
				map.put("href", content.getUrl());
				map.put("src", content.getPic());
				map.put("srcB", content.getPic2());
				map.put("width", 670);
				map.put("widthB", 550);
				bigAdData.add(map);
			}
			
			bigAdJsonData = objectMapper.writeValueAsString(bigAdData);
			try{
				/** 把大广告数据存入Redis数据库中  */
				redisService.setex(REDIS_INDEX_BIGAD, bigAdJsonData, 60 * 60 * 24);
			}catch(Exception ex){}
			
			return objectMapper.writeValueAsString(bigAdData);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}