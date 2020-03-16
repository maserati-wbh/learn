package com.taotao.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品描述
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年4月14日 下午10:53:06
 * @version 1.0
 */
@Table(name = "tb_item_desc")
public class ItemDesc implements Serializable{
    
	private static final long serialVersionUID = 8355219756782004887L;
	/** 商品ID,对应tb_item中的id */
	@Id @Column(name = "item_id")
    private Long itemId;
	/** 商品描述 */
    @Column(name = "item_desc")
    private String itemDesc;
    /** 创建时间 */
    @Column
    private Date created;
    /** 更新时间 */
    @Column
    private Date updated;

    /** setter and getter method */
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getItemDesc() {
        return itemDesc;
    }
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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