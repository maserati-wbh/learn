package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.cart.pojo.CartItem;

/**
 * Cookie购物车服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月14日 上午11:57:33
 * @version 1.0
 */
public interface CookieCartService {
	/**
	 * 加入购物车
	 * @param itemId 商品id
	 * @param buyNum 购买数量
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	void addCart(Long itemId, Integer buyNum, HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 查询购物车
	 * @param request 请求对象
	 * @return List
	 */
	List<CartItem> findCart(HttpServletRequest request);
	/**
	 * 修改购物车
	 * @param itemId 商品id
	 * @param buyNum 购买数量
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	void updateCart(Long itemId, Integer buyNum, HttpServletRequest request,
			HttpServletResponse response);
	/**
	 * 删除购物车中的商品
	 * @param itemId 商品id
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	void deleteCart(Long itemId, HttpServletRequest request,
			HttpServletResponse response);

}
