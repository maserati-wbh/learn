package com.taotao.order.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.taotao.order.pojo.Order;

/**
 * 订单数据访问接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月15日 上午11:22:05
 * @version 1.0
 */
public interface OrderMapper {
	
	/** 保存订单 */
	void save(Order order);
	
	/** 根据订单编号查询订单 */
	Order get(@Param("orderId")String orderId);
	
	/** 关闭订单 */
	void closeOrder(Date date);

}
