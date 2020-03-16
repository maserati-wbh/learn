package com.taotao.cart.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车商品数据传输类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年5月4日 下午9:44:22
 * @version 1.0
 */
public class CartItem implements Serializable{

	private static final long serialVersionUID = -6032931487758717538L;
	/** 用户编号 */
	private Long userId;
	/** 商品编号 */
	private Long itemId;
	/** 商品标题 */
	private String itemTitle;
	/** 商品图片 */
	private String itemImage;
	/** 商品价格 */
	private Long itemPrice;
	/** 购买数量 */
	private Integer num;
	/** 创建日期 */
	private Date created;
	/** 修改日期 */
	private Date updated;
	
	/** setter and getter method */
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public Long getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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