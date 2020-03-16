package com.taotao.admin.service.base;

import java.io.Serializable;
import java.util.List;

/**
 * 基础的业务处理接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午4:08:16
 * @version 1.0
 */
public interface BaseService<T> {
	
	/** 选择性添加 */
	void insertSelective(T entity);
	
	/** 选择性修改 */
	void updateSelective(T entity);
	
	/** 根据主键id做删除 */
	void delete(Serializable id);
	
	/** 批量删除*/
	void deleteAll(String idField, Serializable[] ids);
	
	/** 根据主键id查询 */
	T findOne(Serializable id);
	
	/** 查询全部 */
	List<T> findAll();
	
	/** 等于号多条件查询 */
	List<T> findAllByWhere(T entity);
	
	/** 等于号多条件统计查询 */
	int countByWhere(T entity);
	
	/** 分页查询 */
	List<T> findByPage(int pageNum, int pageSize);
	
}
