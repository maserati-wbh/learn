package com.taotao.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.admin.mapper.ContentCategoryMapper;
import com.taotao.admin.pojo.ContentCategory;
import com.taotao.admin.service.ContentCategoryService;
import com.taotao.admin.service.base.BaseServiceImpl;

/**
 * 内容分类业务处理接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午3:47:33
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements
		ContentCategoryService {
	
	/** 注入数据访问接口代理对象 */
	@Autowired
	private ContentCategoryMapper contentCategoryMapper; 
	/**
	 * 根据父节点查询所有的子节点
	 * @param parentId 父节点
	 * @return List
	 */
	public List<Map<String, Object>> selectContentCategoryByParentId(Long parentId){
		try{
			List<Map<String, Object>> data = contentCategoryMapper.selectContentCategoryByParentId(parentId);
			for (Map<String, Object> map : data){
				map.put("state", (boolean)map.get("state") ? "closed" : "open");
			}
			return data;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 添加分类
	 * @param contentCategory 分类对象
	 * @return 主键id
	 */
	public Long saveContentCategory(ContentCategory contentCategory){
		try{
			/** 添加子节点 */
			contentCategory.setCreated(new Date());
			contentCategory.setIsParent(false);
			contentCategory.setSortOrder(1);
			contentCategory.setStatus(1);
			contentCategory.setUpdated(contentCategory.getCreated());
			contentCategoryMapper.insertSelective(contentCategory);
			
			/** 修改父节点 */
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(true);
			parent.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(parent);
			
			return contentCategory.getId();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 删除分类
	 * @param contentCategory 分类对象
	 */
	public void deleteContentCategory(ContentCategory contentCategory){
		try{
			/** 定义List集合封装所有需要删除的树节点的id */
			List<Long> ids = new ArrayList<>();
			ids.add(contentCategory.getId());
			/** 递归根据父节点查询所有的子节点 */
			findLeafNode(ids, contentCategory.getId());
			/** 批量删除 */
			this.deleteAll("id", ids.toArray(new Long[ids.size()]));
			
			/** 判断当前删除的节点的父节点是否还有子节点 */
			/** select count(*) from tb_content_category where parent_id = 2 */
			ContentCategory parent = new ContentCategory();
			parent.setParentId(contentCategory.getParentId());
			int count = contentCategoryMapper.selectCount(parent);
			/** 如果count等于零，代表没有子节点 */
			if (count == 0){
				/** update from tb_content_category set is_parent=0 where id = parentId */
				parent = new ContentCategory();
				parent.setId(contentCategory.getParentId());
				parent.setIsParent(false);
				parent.setUpdated(new Date());
				contentCategoryMapper.updateByPrimaryKeySelective(parent);
			}
			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 递归查询子节点的方法 */
	private void findLeafNode(List<Long> ids, Long id) {
		/** select * from tb_content_category where parent_id = id */
		/** 定义cc对象封装查询条件 */
		ContentCategory cc = new ContentCategory();
		cc.setParentId(id);
		List<ContentCategory> lists = contentCategoryMapper.select(cc);
		/** 判断条件，作为递归退出条件 */
		if (lists != null && lists.size() > 0){
			for (ContentCategory leafNode : lists){
				/** 添加子节点的id */
				ids.add(leafNode.getId());
				findLeafNode(ids, leafNode.getId());
			}
		}
	}
}