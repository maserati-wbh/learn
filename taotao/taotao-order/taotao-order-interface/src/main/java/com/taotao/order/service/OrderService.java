package com.taotao.order.service;

import com.taotao.order.pojo.Order;

/**
 * 订单服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月15日 上午11:25:16
 * @version 1.0
 */
public interface OrderService {
	/**
	 * 保存订单 
	 * @param order 订单实体
	 * @return 订单号
	 */
	String saveOrder(Order order);
	/**
	 * 根据订单编号查询订单
	 * @param orderId 订单号
	 * @return 订单
	 */
	Order getOrder(String orderId);
	
	/** 自动关闭订单 */
	void autoCloseOrder();

}
