package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.cart.pojo.CartItem;
import com.taotao.common.cookie.CookieUtils;
import com.taotao.portal.service.CookieCartService;

/**
 * Cookie购物车服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月14日 上午11:58:18
 * @version 1.0
 */
@Component
public class CookieCartServiceImpl implements CookieCartService {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private ItemService itemSerivce;
	/** 定义购物车数据存放在Cookie中有效时间 (7天) */
	private final static int CART_MAX_AGE = 60 * 60 * 24 * 7;
	
	/**
	 * 加入购物车
	 * @param itemId 商品id
	 * @param buyNum 购买数量
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public void addCart(Long itemId, Integer buyNum, HttpServletRequest request,
			HttpServletResponse response){
		try{
			/** 查询购物车 */
			List<CartItem> cartItems = findCart(request);
			/** 定义是否购买过的标识符 */
			boolean isBuy = false;
			/** 判断购物车 */
			if (cartItems != null && cartItems.size() > 0){
				/** 迭代购物车 */
				for (CartItem cartItem : cartItems){
					/** 判断商品是否购买过 */
					if (cartItem.getItemId().equals(itemId)){
						/** 已购买 */
						cartItem.setNum(cartItem.getNum() + buyNum);
						cartItem.setUpdated(new Date());
						isBuy = true;
						break;
					}
				}
			}else{
				cartItems = new ArrayList<>(1);
			}
			
			if (!isBuy){
				/** 购物车中不存在该商品 (新增该商品) */
				CartItem cartItem = new CartItem();
				cartItem.setCreated(new Date());
				Item item = itemSerivce.findOne(itemId);
				cartItem.setItemId(itemId);
				/** 设置一张商品图片 */
				if (item.getImages() != null && item.getImages().length > 0){
					cartItem.setItemImage(item.getImages()[0]);
				}
				cartItem.setItemPrice(item.getPrice());
				cartItem.setItemTitle(item.getTitle());
				cartItem.setNum(buyNum);
				cartItem.setUpdated(cartItem.getCreated());
				cartItems.add(cartItem);
			}
			
			/** 把购物车数据存储到Cookie中 */
			CookieUtils.setCookie(request, response, 
					CookieUtils.CookieName.TAOTAO_CART, 
					objectMapper.writeValueAsString(cartItems), CART_MAX_AGE, true);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 查询购物车
	 * @param request 请求对象
	 * @return List
	 */
	public List<CartItem> findCart(HttpServletRequest request){
		try{
			/** 从Cookie中获取购物车数据  List<CartItem> [{},{}]*/
			String cartItemListJsonStr = CookieUtils.getCookieValue(request, 
						CookieUtils.CookieName.TAOTAO_CART, true);
			if (StringUtils.isNoneBlank(cartItemListJsonStr)){
				return objectMapper.readValue(cartItemListJsonStr, objectMapper.getTypeFactory()
						.constructCollectionLikeType(List.class, CartItem.class));
			}
			return null;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 修改购物车
	 * @param itemId 商品id
	 * @param buyNum 购买数量
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public void updateCart(Long itemId, Integer buyNum, HttpServletRequest request,
			HttpServletResponse response){
		try{
			/** 查询购物车 */
			List<CartItem> cartItems = findCart(request);
			if (cartItems != null && cartItems.size() > 0){
				/** 循环购物车中的商品 */
				for (CartItem cartItem : cartItems){
					/** 判断商品是否购买过 */
					if (cartItem.getItemId().equals(itemId)){
						cartItem.setNum(buyNum);
						cartItem.setUpdated(new Date());
						/** 把购物车数据存储到Cookie中 */
						CookieUtils.setCookie(request, response, 
								CookieUtils.CookieName.TAOTAO_CART, 
								objectMapper.writeValueAsString(cartItems), CART_MAX_AGE, true);
						break;
					}
				}
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 删除购物车中的商品
	 * @param itemId 商品id
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public void deleteCart(Long itemId, HttpServletRequest request,
			HttpServletResponse response){
		try{
			/** 查询购物车 */
			List<CartItem> cartItems = findCart(request);
			if (cartItems != null && cartItems.size() > 0){
				/** 得到集合的迭代器 */
				Iterator<CartItem> iter =  cartItems.iterator();
				while (iter.hasNext()){
					CartItem cartItem = iter.next();
					if (cartItem.getItemId().equals(itemId)){
						/** 删除集合中的元素 */
						iter.remove();
					}
				}
			}
			if (cartItems != null && cartItems.size() > 0){
				/** 把购物车数据存储到Cookie中 */
				CookieUtils.setCookie(request, response, 
						CookieUtils.CookieName.TAOTAO_CART, 
						objectMapper.writeValueAsString(cartItems), CART_MAX_AGE, true);
			}else{
				// 删除购物车对应的cookie(购物车没有商品)
				CookieUtils.deleteCookie(request, response, CookieUtils.CookieName.TAOTAO_CART);
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}