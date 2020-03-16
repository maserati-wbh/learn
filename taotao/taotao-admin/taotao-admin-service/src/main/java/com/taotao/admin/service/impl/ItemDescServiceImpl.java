package com.taotao.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.admin.mapper.ItemDescMapper;
import com.taotao.admin.pojo.ItemDesc;
import com.taotao.admin.service.ItemDescService;
import com.taotao.admin.service.base.BaseServiceImpl;

/**
 * 商品描述业务处理接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午6:04:54
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class ItemDescServiceImpl extends BaseServiceImpl<ItemDesc> implements ItemDescService {
	@Autowired
	private ItemDescMapper itemDescMapper;
	
}
