package com.taotao.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.taotao.admin.pojo.ContentCategory;

import tk.mybatis.mapper.common.Mapper;

/**
 * 内容分类数据访问接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月27日 下午3:43:46
 * @version 1.0
 */
public interface ContentCategoryMapper extends Mapper<ContentCategory> {
	/**
	 * 根据父节点查询所有的子节点
	 * @param parentId 父节点
	 * @return List
	 */
	@Select("SELECT id,name as text, is_parent as state, parent_id as parentId FROM "
			+ "`tb_content_category` where parent_id = #{parentId} order by id asc")
	List<Map<String, Object>> selectContentCategoryByParentId(Long parentId);

}
