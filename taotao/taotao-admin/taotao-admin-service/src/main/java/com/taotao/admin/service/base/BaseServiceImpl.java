package com.taotao.admin.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 基础的业务处理接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月24日 下午4:16:08
 * @version 1.0
 */
public class BaseServiceImpl<T> implements BaseService<T> {
	
	/** 注入通用Mapper接口代理对象 */
	@Autowired
	private Mapper<T> mapper;
	/** 定义实体类的类型 */
	private Class<T> entityClass;
	
	/** 构造器 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl(){
		/** 获取父接口上所有泛型参数类型 BaseService<T>  */
		ParameterizedType parameterizedType  = 
				(ParameterizedType)this.getClass().getGenericSuperclass();
		/** 获取实际的参数的类型 */
		entityClass = (Class<T>)parameterizedType
				.getActualTypeArguments()[0];
	}
	
	/** 选择性添加 */
	public void insertSelective(T entity){
		mapper.insertSelective(entity);
	}
	/** 选择性修改 */
	public void updateSelective(T entity){
		mapper.updateByPrimaryKeySelective(entity);
	}
	/** 根据主键id做删除 */
	public void delete(Serializable id){
		mapper.deleteByPrimaryKey(id);
	}
	
	/** 批量删除*/
	public void deleteAll(String idField, Serializable[] ids){
		/** delete form 表  where id in(?,?,?)*/
		/** 创建删除条件Example */
		Example example = new Example(entityClass);
		/** 创建条件对象 */
		Criteria criteria = example.createCriteria();
		/** 添加一个条件 id in () */
		criteria.andIn(idField, Arrays.asList(ids));
		mapper.deleteByExample(example);
	}
	
	/** 根据主键id查询 */
	public T findOne(Serializable id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/** 查询全部 */
	public List<T> findAll(){
		return mapper.selectAll();
	}
	
	/** 等于号多条件查询 */
	public List<T> findAllByWhere(T entity){
		return mapper.select(entity);
	}
	
	/** 等于号多条件统计查询 */
	public int countByWhere(T entity){
		return mapper.selectCount(entity);
	}
	
	/** 分页查询 */
	public List<T> findByPage(int pageNum, int pageSize){
		/** 开启分页 */
		PageHelper.startPage(pageNum, pageSize);
		return findAll();
	}
}
