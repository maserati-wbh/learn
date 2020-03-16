package com.taotao.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.taotao.admin.pojo.Item;

import tk.mybatis.mapper.common.Mapper;

/**
 * 商品数据访问接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午6:01:55
 * @version 1.0
 */
public interface ItemMapper extends Mapper<Item> {
	
	/** 根据条件查询商品 */
	List<Map<String,Object>> findAll(@Param("item")Item item);

}
