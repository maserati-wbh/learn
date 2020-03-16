package com.taotao.admin.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.mapper.ItemMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemService;
import com.taotao.admin.service.base.BaseServiceImpl;
import com.taotao.common.vo.DataGridResult;

/**
 * 商品业务处理接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午6:04:54
 * @version 1.0
 */
@Service
@Transactional(readOnly=false, rollbackFor=RuntimeException.class)
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	/** 添加商品 */
	public Long saveItem(Item item, String desc){
		try{
			/** 添加商品 */
			item.setCreated(new Date());
			item.setStatus(1);
			item.setUpdated(item.getCreated());
			itemMapper.insertSelective(item);
			
			/** 添加商品描述 */
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(itemDesc.getCreated());
			itemDescMapper.insertSelective(itemDesc);
			
			return item.getId();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 修改商品 */
	public void updateItem(Item item, String desc){
		try{
			/** 修改商品 */
			item.setUpdated(new Date());
			itemMapper.updateByPrimaryKeySelective(item);
			
			/** 修改商品描述 */
			ItemDesc itemDesc = new ItemDesc();
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			itemDesc.setUpdated(new Date());
			itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 分页查询商品 */
	public DataGridResult selectItemByPage(final Item item, Integer page, Integer rows){
		try{
			/** 开启分页 */
			PageInfo<Map<String,Object>> pageInfo = PageHelper
					.startPage(page, rows).doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					itemMapper.findAll(item);
				}
			});
			/** 创建datagrid组件最终需要的数据对象 */
			DataGridResult dataGridResult = new DataGridResult();
			dataGridResult.setTotal(pageInfo.getTotal());
			dataGridResult.setRows(pageInfo.getList());
			return dataGridResult;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
