package com.taotao.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 物品类目
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年4月14日 上午8:34:54
 * @version 1.0
 */
@Table(name = "tb_item_cat")
public class ItemCat implements Serializable {

	private static final long serialVersionUID = 3015396524384332740L;
	/** 类目编号 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	/** 类目父级编号 */
    @Column(name = "parent_id")
    private Long parentId;
    /** 类目名称 */
    private String name;
    /** 类目状态 */
    private Integer status;
    /** 类目排序 */
    @Column(name = "sort_order")
    private Integer sortOrder;
    /** 是否为父级 */
    @Column(name = "is_parent")
    private Boolean isParent;
    /** 创建日期 */
    private Date created;
    /** 修改日期 */
    private Date updated;
    
    /** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}