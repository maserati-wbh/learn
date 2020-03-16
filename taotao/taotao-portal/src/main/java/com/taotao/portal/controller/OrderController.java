package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.pojo.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 订单处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月15日 上午8:49:45
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	/** 注入用户服务接口代理对象 */
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	/** 
	 * 跳转到订单结算页面
	 * http://www.taotao.com/order/balance.html
	 */
	@GetMapping("/balance")
	public String balance(HttpServletRequest request, Model model){
		try{
			/** 获取登录用户 */
			User user = (User)request.getAttribute("user");
			/** 获取该用户的购物车数据 */
			List<CartItem> carts = cartService.findCartByUserId(user.getId());
			model.addAttribute("carts", carts);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "order-cart";
	}
	
	/** 提交订单 */
	@PostMapping("/submit")
	public @ResponseBody Map<String, Object> submit(HttpServletRequest request, Order order){
		/** 定义Map集合封装响应数据 */
		Map<String, Object> data = new HashMap<>();
		data.put("status", 500);
		try{
			/** 获取登录用户 */
			User user = (User)request.getAttribute("user");
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			/** 保存订单 */
			String orderId = orderService.saveOrder(order);
			/** 清空购物车 */
			cartService.clearCart(user.getId());
			data.put("status", 200);
			data.put("orderId", orderId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return data;
	}
	
	/** 跳转到订单成功页面 */
	@GetMapping("/success")
	public String success(@RequestParam("id")String orderId, Model model){
		try{
			/** 根据订单号查询订单 */
			// ${order.orderId }
			// ${date}
			Order order = orderService.getOrder(orderId);
			model.addAttribute("order", order);
			/** 送达时间 (时间操作组件)*/
			model.addAttribute("date", DateTime.now().plusDays(2).toString("MM月dd日"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
}
