package com.taotao.admin.service;

import com.taotao.admin.pojo.Content;
import com.taotao.admin.service.base.BaseService;
import com.taotao.common.vo.DataGridResult;

/**
 * 内容业务处理接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午3:45:55
 * @version 1.0
 */
public interface ContentService extends BaseService<Content> {
	
	/**
	 * 根据分类分页查询内容
	 * @param page 当前页码
	 * @param rows 每页显示记录数
	 * @param categoryId 分类id
	 * @return easyui的datagrid组件需要的数据
	 */
	DataGridResult selectContentByPage(Integer page, Integer rows,
			Long categoryId);
	/**
	 * 查询大广告数据
	 * @return JSON格式的字符串
	 */
	String findBigAdData();

}
