package com.taotao.admin.service;

import java.util.List;
import java.util.Map;

import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.base.BaseService;

/**
 * 内容分类业务处理接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午3:45:55
 * @version 1.0
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {
	
	/**
	 * 根据父节点查询所有的子节点
	 * @param parentId 父节点
	 * @return List
	 */
	List<Map<String, Object>> selectContentCategoryByParentId(Long parentId);
	/**
	 * 添加分类
	 * @param contentCategory 分类对象
	 * @return 主键id
	 */
	Long saveContentCategory(ContentCategory contentCategory);
	/**
	 * 删除分类
	 * @param contentCategory 分类对象
	 */
	void deleteContentCategory(ContentCategory contentCategory);

}
