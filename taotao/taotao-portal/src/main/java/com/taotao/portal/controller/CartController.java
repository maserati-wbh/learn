package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;
import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.common.cookie.CookieUtils;
import com.taotao.portal.service.CookieCartService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 购物车处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月14日 上午9:43:04
 * @version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	
	/** 注入服务接口代理对象 */
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CookieCartService cookieCartService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/** 加入购物车 */
	@GetMapping("/{itemId}/{buyNum}")
	public String addCart(@PathVariable("itemId")Long itemId,
			@PathVariable("buyNum")Integer buyNum,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		/** 获取ticket登录票据 */
		String ticket = CookieUtils.getCookieValue(request,
				CookieUtils.CookieName.TAOTAO_TICKET, false);
		/** 判断ticket登录票据 */
		if (StringUtils.isNoneBlank(ticket)){
			/**########### 登录用户，需要把购物车数据加入Redis中 ############### */
			String userJsonStr = userService.findUserByTicket(ticket);
			/** 把userJsonStr转化成user对象 */
			User user = objectMapper.readValue(userJsonStr, User.class);
			/** 根据商品id查询商品 */
			Item item = itemService.findOne(itemId);
			/** 调用cartService加入购物车数据到Redis中 */
			cartService.addCartByUserId(user.getId(),item, buyNum);
		}else{
			/**########### 未登录用户，需要把购物车数据加入Cookie中 ############### */
			cookieCartService.addCart(itemId, buyNum, request, response);
		}
		/** 重定向到查看购物车 */
		return "redirect:/cart/showCart.html";
	}
	
	/** 查看购物车 */
	@GetMapping("/showCart")
	public String showCart(HttpServletRequest request, Model model)
				throws Exception{
		/** 获取ticket登录票据 */
		String ticket = CookieUtils.getCookieValue(request,
				CookieUtils.CookieName.TAOTAO_TICKET, false);
		/** 定义购物车 */
		List<CartItem> cart = null;
		/** 判断ticket登录票据 */
		if (StringUtils.isNoneBlank(ticket)){
			/**########### 登录用户，从Redis中获取购物车数据 ############### */
			String userJsonStr = userService.findUserByTicket(ticket);
			/** 把userJsonStr转化成user对象 */
			User user = objectMapper.readValue(userJsonStr, User.class);
			/** 调用cartService从Redis中获取购物车数据 */
			cart = cartService.findCartByUserId(user.getId());
		}else{
			/**########### 未登录用户，从Cookie中获取购物车数据 ############### */
			cart = cookieCartService.findCart(request);
		}
		
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	/**
	 * 修改购物车
	 * http://www.taotao.com/cart/update/10477944071/7
	 */
	@PostMapping("/update/{itemId}/{buyNum}")
	public ResponseEntity<Void> updateCart(@PathVariable("itemId")Long itemId,
							@PathVariable("buyNum")Integer buyNum,
							HttpServletRequest request,
							HttpServletResponse response){
		try{
			/** 获取ticket登录票据 */
			String ticket = CookieUtils.getCookieValue(request,
					CookieUtils.CookieName.TAOTAO_TICKET, false);
			/** 判断ticket登录票据 */
			if (StringUtils.isNoneBlank(ticket)){
				/**########### 登录用户，从Redis中修改购物车数据 ############### */
				String userJsonStr = userService.findUserByTicket(ticket);
				/** 把userJsonStr转化成user对象 */
				User user = objectMapper.readValue(userJsonStr, User.class);
				/** 调用cartService从Redis中修改购物车数据 */
				cartService.updateCartByUserId(user.getId(), itemId, buyNum);
			}else{
				/**########### 未登录用户，从Cookie中获取购物车数据 ############### */
				cookieCartService.updateCart(itemId, buyNum, request, response);
			}
			
			return ResponseEntity.ok().build();
		}catch(Exception ex){
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 删除购物车中的商品
	 * http://www.taotao.com/cart/delete/1963334970.html
	 */
	@GetMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable("itemId")Long itemId,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		/** 获取ticket登录票据 */
		String ticket = CookieUtils.getCookieValue(request,
				CookieUtils.CookieName.TAOTAO_TICKET, false);
		/** 判断ticket登录票据 */
		if (StringUtils.isNoneBlank(ticket)){
			/**########### 登录用户，从Redis中删除购物车数据 ############### */
			String userJsonStr = userService.findUserByTicket(ticket);
			/** 把userJsonStr转化成user对象 */
			User user = objectMapper.readValue(userJsonStr, User.class);
			/** 调用cartService从Redis中删除购物车数据 */
			cartService.deleteCartByUserId(user.getId(), itemId);
		}else{
			/**########### 未登录用户，从Cookie中删除购物车数据 ############### */
			cookieCartService.deleteCart(itemId, request, response);
		}
		/** 重定向到查看购物车 */
		return "redirect:/cart/showCart.html";
	}
}
