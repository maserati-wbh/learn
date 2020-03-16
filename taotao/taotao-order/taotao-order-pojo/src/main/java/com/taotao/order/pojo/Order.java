package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年5月7日 上午11:30:52
 * @version 1.0
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 4224146901668070933L;
	/** orderId 订单唯一标识符: 用户编号 + 时间戳  */
	private String orderId;
	/** 实付金额 */
	private String payment;
	/** 支付类型，1、在线支付，2、货到付款 */
	private Integer paymentType;
	/** 邮费 */
	private String postFee;
	/** 状态:1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭 */
	private Integer status;
	/** 创建时间  */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 付款时间 */
	private Date paymentTime;
	/** 发货时间 */
	private Date consignTime;
	/** 交易结束时间 */
	private Date endTime;
	/** 交易关闭时间 */
	private Date closeTime;
	/** 物流名称 */
	private String shippingName;
	/** 物流单号 */
	private String shippingCode;
	/** 用户id */
	private Long userId;
	/** 买家留言 */
	private String buyerMessage;
	/** 买家昵称 */
	private String buyerNick;
	/** 买家是否已经评价 */
	private Integer buyerRate;
	/** 商品详情(订单 与  订单商品 存在一对多关联) */
	private List<OrderItem> orderItems;
	/** 订单运送 (订单 与 订单运送  存在一对一关联) */
	private OrderShipping orderShipping;
	
	/** setter and getter method */
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public OrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public Integer getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}