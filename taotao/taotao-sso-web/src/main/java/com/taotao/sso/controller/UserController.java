package com.taotao.sso.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.service.CartService;
import com.taotao.common.cookie.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 用户处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:25:32
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	/** 注入服务层接口代理对象 */
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 验证用户名、手机号码、邮箱是否重复
	 * @param param 用户名｜手机号码｜邮箱
	 * @param type 1、2、3分别代表username、phone、email
	 * @return
	 */
	@GetMapping("/check/{param}/{type}")
	public ResponseEntity<Boolean> validate(@PathVariable("param")String param, 
				@PathVariable("type")Integer type){
		try{
			/** 数据判断 */
			if (type != null && type >=1 && type <= 3){
				Boolean isPass = userService.validate(param, type);
				return ResponseEntity.ok(isPass);
			}
			/** 400：请求参数不合法 */
			return ResponseEntity.badRequest().body(null);
		}catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/** 用户注册 */
	@PostMapping("/register")
	public @ResponseBody Map<String, String> register(User user){
		Map<String, String> data = new HashMap<>();
		data.put("status", "500");
		try{
			/** 数据判断 */
			if (user != null){
				userService.saveUser(user);
				data.put("status", "200");
			}
		}catch(Exception ex){
			data.put("data", "服务器内部错误！");
			ex.printStackTrace();
		}
		return data;
	}
	
	/** 用户登录 */
	@PostMapping("/login")
	public @ResponseBody Map<String, Integer> login(User user,
				HttpServletRequest request, HttpServletResponse response){
		Map<String, Integer> data = new HashMap<>();
		data.put("status", 500);
		try{
			/** 数据判断 */
			if (user != null){
				/** 调用服务层的登录方法，返回ticket登录票据 */
				String ticket = userService.login(user);
				/** 把ticket存入Cookie，保证cookie跨域访问 */
				CookieUtils.setCookie(request, response, 
						CookieUtils.CookieName.TAOTAO_TICKET,
						ticket, -1, false);
				
				/** ############ 把Cookie中的购物车数据合并到Redis中 ############# */
				/** 从Cookie中获取购物车数据 */
				String cookieCartItems = CookieUtils
						.getCookieValue(request, CookieUtils.CookieName.TAOTAO_CART, true);
				/** 判断购物车数据 */
				if (StringUtils.isNoneBlank(cookieCartItems)){
					/** 根据ticket票据查询用户 */
					User u = objectMapper.readValue(userService.findUserByTicket(ticket), User.class);
					/** 合并购物车 */
					cartService.megreCart(u.getId(),cookieCartItems);
					/** 把购物车对应的Cookie删除 */
					CookieUtils.deleteCookie(request, response, CookieUtils.CookieName.TAOTAO_CART);
				}
				data.put("status", 200);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return data;
	}
	
	/** 根据ticket票据查询登录用户信息 */
	@GetMapping("/{ticket}")
	public ResponseEntity<String> findUserByTicket(@PathVariable("ticket")String ticket,
			@RequestParam(value="callback", required=false)String callback){
		try{
			/** 根据ticket票据查询登录用户信息 */
			String userJsonStr = userService.findUserByTicket(ticket);
			StringBuilder sb = new StringBuilder();
			// 支持 json 与 jsonp
			if (StringUtils.isNoneBlank(callback)){
				// jsonp
				sb.append(callback + "("+ userJsonStr +");");
			}else{
				// json
				sb.append(userJsonStr);
			}
			return ResponseEntity.ok(sb.toString());
		}catch(Exception ex){
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/** 用户退出 */
	@GetMapping("/logout")
	public String logout(@RequestParam(value="redirectURL", 
			defaultValue="http://www.taotao.com")String redirectURL,
			HttpServletRequest request, HttpServletResponse response){
		
		/** 从Cookie中获取ticket票据 */
		String ticket = CookieUtils.getCookieValue(request,
				CookieUtils.CookieName.TAOTAO_TICKET, false);
		if (StringUtils.isNoneBlank(ticket)){
			/** 根据ticket票据删除Redis中用户数据 */
			userService.delTicket(ticket);
			/** 删除Cookie */
			CookieUtils.deleteCookie(request, response, 
					CookieUtils.CookieName.TAOTAO_TICKET);
		}
		/** 重定向到指定url */
		return "redirect:" + redirectURL;
	}
}
