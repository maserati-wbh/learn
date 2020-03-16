package com.taotao.order.service.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.order.mapper.OrderMapper;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

/**
 * 订单服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月15日 上午11:25:44
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	/**
	 * 保存订单 
	 * @param order 订单实体
	 * @return 订单号
	 */
	public String saveOrder(Order order){
		try{
			/** 生成订单编号 */
			String orderId = order.getUserId().toString() + System.currentTimeMillis();
			order.setOrderId(orderId);
			order.setCreateTime(new Date());
			order.setUpdateTime(order.getCreateTime());
			order.setStatus(1);
			orderMapper.save(order);
			return orderId;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 根据订单编号查询订单
	 * @param orderId 订单号
	 * @return 订单
	 */
	public Order getOrder(String orderId){
		try{
			return orderMapper.get(orderId);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/** 自动关闭订单 (定时调度方法)*/
	public void autoCloseOrder(){
		try{
			System.out.println("==========autoCloseOrder==========");
			orderMapper.closeOrder(DateTime.now().minusDays(2).toDate());
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
