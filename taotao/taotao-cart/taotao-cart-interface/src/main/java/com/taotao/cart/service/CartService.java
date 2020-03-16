package com.taotao.cart.service;

import java.util.List;

import com.taotao.admin.pojo.Item;
import com.taotao.cart.pojo.CartItem;

/**
 * 购物车服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月14日 上午9:09:10
 * @version 1.0
 */
public interface CartService {
	/**
	 * 加入购物车
	 * @param id 用户id
	 * @param item 商品对象
	 * @param buyNum 购买数量
	 */
	void addCartByUserId(Long id, Item item, Integer buyNum);
	/**
	 * 查询购物车数据
	 * @param id 用户id
	 * @return 购物车
	 */
	List<CartItem> findCartByUserId(Long id);
	/**
	 * 修改购物车
	 * @param id 用户id
	 * @param itemId 商品id
	 * @param buyNum  购买数量
	 */
	void updateCartByUserId(Long id, Long itemId, Integer buyNum);
	/**
	 * 删除购物车中的商品
	 * @param id 用户id
	 * @param itemId 商品id
	 */
	void deleteCartByUserId(Long id, Long itemId);
	/**
	 * 合并购物车
	 * @param id 用户id
	 * @param cookieCartItems Cookie中的购物车数据
	 */
	void megreCart(Long id, String cookieCartItems);
	/**
	 * 清空购物车
	 * @param id 用户id
	 */
	void clearCart(Long id);

}
