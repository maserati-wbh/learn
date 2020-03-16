package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * 用户服务接口实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:23:25
 * @version 1.0
 */
@Service
@Transactional(readOnly=false)
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	/** 注入RedisTemplate来操作Redis */
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	/** 定义ticket票据前缀 (当前登录用存放在Redis中key的前缀) */
	private static final String REDIS_TICKET_PREFIX = "ticket_";
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 验证用户名、手机号码、邮箱是否重复
	 * @param param 用户名｜手机号码｜邮箱
	 * @param type 1、2、3分别代表username、phone、email
	 * @return Boolean true : 数据可用  false: 不可用
	 */
	public Boolean validate(String param, Integer type){
		try{
			/** 定义User对象来封装查询条件 */
			User user = new User();
			if (type == 1){ // 用户名
				user.setUsername(param);
			}else if (type == 2){ // 手机号码
				user.setPhone(param);
			}else{ // 邮箱
				user.setEmail(param);
			}
			return userMapper.count(user) == 0;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 添加用户
	 * @param user 用户
	 */
	public void saveUser(User user){
		try{
			user.setCreated(new Date());
			user.setUpdated(user.getCreated());
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			userMapper.save(user);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 用户登录
	 * @param user 用户
	 * @return ticket票据
	 */
	public String login(User user){
		try{
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			/** 根据用户名与密码查询用户 */
			User u = userMapper.getUser(user);
			/** 判断用户 */
			if (u != null){
				/** 生成ticket票据 */
				String ticket = DigestUtils.md5Hex(u.getId().toString() + System.currentTimeMillis());
				/** 用户登录成功，把用户信息存入Redis中 */
				redisTemplate.boundValueOps(REDIS_TICKET_PREFIX + ticket)
					.set(objectMapper.writeValueAsString(u), 1, TimeUnit.HOURS);
				return ticket;
			}
			return null;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 根据ticket票据查询登录用户信息
	 * @param ticket 票据
	 * @return 用户json格式的字符串
	 */
	public String findUserByTicket(String ticket){
		try{
			/** 从Redis中根据key获取Value */
			String userJsonStr = redisTemplate.
					boundValueOps(REDIS_TICKET_PREFIX + ticket).get();
			if (StringUtils.isNoneBlank(userJsonStr)){
				/** 重新设置key的有效时间 */
				redisTemplate.expire(REDIS_TICKET_PREFIX + ticket, 1, TimeUnit.HOURS);
			}
			return userJsonStr;
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 根据ticket票据删除Redis中用户数据
	 * @param ticket 票据
	 */
	public void delTicket(String ticket){
		try{
			redisTemplate.delete(REDIS_TICKET_PREFIX + ticket);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}