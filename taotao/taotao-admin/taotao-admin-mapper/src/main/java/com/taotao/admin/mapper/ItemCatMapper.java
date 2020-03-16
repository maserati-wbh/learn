package com.taotao.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.taotao.admin.pojo.ItemCat;

import tk.mybatis.mapper.common.Mapper;

/**
 * 商品类目数据访问接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月23日 下午6:41:09
 * @version 1.0
 */
public interface ItemCatMapper extends Mapper<ItemCat> {
	
	/** 根据父节点查询所有的子节点 */
	@Select("SELECT id, name as text, is_parent as state FROM `tb_item_cat` where parent_id = #{parentId} order by id asc")
	List<Map<String, Object>> selectItemCatByParentId(Long parentId);
	
}