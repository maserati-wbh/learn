package com.taotao.portal.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.cookie.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 登录拦截器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月15日 上午9:10:26
 * @version 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	/** 注入用户服务接口代理对象 */
	@Autowired
	private UserService userService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/** 在执行处理请求方法之前进来 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		/**############### 判断用户是否登录 ##############*/
		/** 获取Cookie中用户登录的ticket票据 */
		String ticket = CookieUtils.getCookieValue(request, 
				CookieUtils.CookieName.TAOTAO_TICKET, false);
		/** 判断ticket是否为空 */
		if (StringUtils.isNoneBlank(ticket)){
			/** 根据ticket票据获取用户登录信息 */
			String userJsonStr = userService.findUserByTicket(ticket);
			if (StringUtils.isNoneBlank(userJsonStr)){
				User user = objectMapper.readValue(userJsonStr, User.class);
				request.setAttribute("user", user);
				return true;
			}
		}
		/** 
		 * 获取查询字符串  userId=admin
		 * http://www.taotao.com/order/balance.html?userId=admin
		 **/
		String queryStr = request.getQueryString();
		/** 获取请求URL */
		String redirectURL = request.getRequestURL().toString();
		/** 判断查询字符串是否为空 */
		if (StringUtils.isNoneBlank(queryStr)){
			redirectURL += "?" + queryStr;
		}
		/** 用户没有登录，重定向到单点登录系统的登录页面 */
		response.sendRedirect("http://sso.taotao.com/login?redirectURL=" 
						+ URLEncoder.encode(redirectURL, "utf-8"));
		return false;
	}
}
