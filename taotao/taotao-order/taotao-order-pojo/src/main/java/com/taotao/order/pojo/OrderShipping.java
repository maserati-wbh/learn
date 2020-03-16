package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 * 订单运送
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年5月7日 上午11:36:04
 * @version 1.0
 */
public class OrderShipping implements Serializable {

	private static final long serialVersionUID = -7061920507000794667L;
	/** 订单ID */
	private String orderId;
	/** 收货人全名 */
	private String receiverName;
	/** 固定电话 */
	private String receiverPhone;
	/** 移动电话 */
	private String receiverMobile;
	/** 省份 */
	private String receiverState;
	/** 城市 */
	private String receiverCity;
	/** 区/县 */
	private String receiverDistrict;
	/** 收货地址，如：xx路xx号 */
	private String receiverAddress;
	/** 邮政编码,如：510000 */
	private String receiverZip;
	/** 创建时间 */
	private Date created;
	/** 修改时间 */
	private Date updated;
	
	/** setter and getter method */
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
}