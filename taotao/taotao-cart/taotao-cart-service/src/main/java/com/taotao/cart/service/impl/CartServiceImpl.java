package com.taotao.cart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;

/**
 * 购物车服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月14日 上午9:11:36
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {
	
	/** 注入RedisTemplate对象操作Redis */
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	/** 定义用户购物车存放在Redis中key的前缀 */
	private final static String REDIS_CART_PREFIX = "cart_";
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 加入购物车
	 * @param id 用户id
	 * @param item 商品对象
	 * @param buyNum 购买数量
	 */
	public void addCartByUserId(Long id, Item item, Integer buyNum){
		try{
			/** 
			 * Redis中怎样存储一个用户的购物车数据 
			 * key -- value
			 * key : 前缀_用户id
			 * value: hash: Map<商品id, CartItem>
			 */
			String key = REDIS_CART_PREFIX + id;
			
			String cartItemJsonStr = null;
			/** 判断key是否存在 */
			if (redisTemplate.hasKey(key)){
				/** 获取CartItem对象的json字符串 */
				cartItemJsonStr = (String)redisTemplate.boundHashOps(key)
						.get(item.getId().toString());
			}
			/** 定义购物车中商品对象 */
			CartItem cartItem = null;
			if (StringUtils.isNoneBlank(cartItemJsonStr)){
				/** 购物车中已存在该商品 (购买过，数量叠加) */
				/** 把cartItemJsonStr转化成CartItem对象 */
				cartItem = objectMapper.readValue(cartItemJsonStr, CartItem.class);
				cartItem.setNum(cartItem.getNum() + buyNum);
				cartItem.setUpdated(new Date());
			}else{
				/** 购物车中不存在该商品 (新增该商品) */
				cartItem = new CartItem();
				cartItem.setCreated(new Date());
				cartItem.setItemId(item.getId());
				/** 设置一张商品图片 */
				if (item.getImages() != null && item.getImages().length > 0){
					cartItem.setItemImage(item.getImages()[0]);
				}
				cartItem.setItemPrice(item.getPrice());
				cartItem.setItemTitle(item.getTitle());
				cartItem.setNum(buyNum);
				cartItem.setUpdated(cartItem.getCreated());
				cartItem.setUserId(id);
			}
			/** 设置商品到Redis中 */
			redisTemplate.boundHashOps(key).put(item.getId().toString(), 
					objectMapper.writeValueAsString(cartItem));
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 查询购物车数据
	 * @param id 用户id
	 * @return 购物车
	 */
	public List<CartItem> findCartByUserId(Long id){
		try{
			/** 定义用户购物车存储在Redis中key */
			String key = REDIS_CART_PREFIX + id;
			/** 获取key的value */
			Map<Object, Object> cartItemMap =  redisTemplate.boundHashOps(key).entries();
			
			if (cartItemMap != null && cartItemMap.size() > 0){
				/** 定义购物车集合 */
				List<CartItem> cart = new ArrayList<>(1);
				/** 迭代cartItemMap的value对应的集合 */
				for (Object obj : cartItemMap.values()){
					// obj --> cartItem的json格式的字符串
					cart.add(objectMapper.readValue(obj.toString(), CartItem.class));
				}
				return cart;
			}
			return null;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 修改购物车
	 * @param id 用户id
	 * @param itemId 商品id
	 * @param buyNum  购买数量
	 */
	public void updateCartByUserId(Long id, Long itemId, Integer buyNum){
		try{
			/** 定义用户购物车存储在Redis中key */
			String key = REDIS_CART_PREFIX + id;
			/** 从Redis中获取需要修改商品 */
			String cartItemJsonStr = (String)redisTemplate.boundHashOps(key)
					.get(itemId.toString());
			if (StringUtils.isNoneBlank(cartItemJsonStr)){
				/** 把cartItemJsonStr转化CartItem */
				CartItem cartItem = objectMapper.readValue(cartItemJsonStr, CartItem.class);
				cartItem.setNum(buyNum);
				cartItem.setUpdated(new Date());
				/** 重新设置到Redis中 */
				redisTemplate.boundHashOps(key)
					.put(itemId.toString(), objectMapper.writeValueAsString(cartItem));
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 删除购物车中的商品
	 * @param id 用户id
	 * @param itemId 商品id
	 */
	public void deleteCartByUserId(Long id, Long itemId){
		try{
			/** 定义用户购物车存储在Redis中key */
			String key = REDIS_CART_PREFIX + id;
			redisTemplate.boundHashOps(key).delete(itemId.toString());
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 合并购物车
	 * @param id 用户id
	 * @param cookieCartItems Cookie中的购物车数据
	 */
	public void megreCart(Long id, String cookieCartItems){
		try{
			/** 把cookieCartItems转化成List<CartItem>集合 */
			List<CartItem> cartItems = objectMapper.readValue(cookieCartItems, 
						objectMapper.getTypeFactory()
						.constructCollectionLikeType(List.class, CartItem.class));
			for (CartItem cookieCartItem : cartItems){
				addCartByUserId(id, cookieCartItem);
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 加入购物车(合并方法)
	 * @param id 用户id
	 * @param cookieCartItem Cookie中商品对象
	 */
	public void addCartByUserId(Long id, CartItem cookieCartItem){
		try{
			/** 定义购物车存放在Redis中的key */
			String key = REDIS_CART_PREFIX + id;
			
			/** 先从Redis购物车中获取商品 */
			String cartItemJsonStr = (String)redisTemplate
					.boundHashOps(key).get(cookieCartItem.getItemId().toString());
			
			/** 定义购物车商品对象 */
			CartItem cartItem = null;
			if (StringUtils.isNoneBlank(cartItemJsonStr)){
				/** 代表购物车中存在该商品，数量叠加 */
				cartItem = objectMapper.readValue(cartItemJsonStr, CartItem.class);
				cartItem.setNum(cartItem.getNum() + cookieCartItem.getNum());
				cartItem.setUpdated(new Date());
			}else{ // 代表Redis购物车中没有购买该商品
				cartItem = cookieCartItem;
				cartItem.setUserId(id); // 登录用户
				cartItem.setUpdated(new Date());
			}
			redisTemplate.boundHashOps(key).put(cookieCartItem.getItemId().toString(),
					objectMapper.writeValueAsString(cartItem));
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 清空购物车
	 * @param id 用户id
	 */
	public void clearCart(Long id){
		try{
			/** 定义购物车存放在Redis中的key */
			String key = REDIS_CART_PREFIX + id;
			redisTemplate.delete(key);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
