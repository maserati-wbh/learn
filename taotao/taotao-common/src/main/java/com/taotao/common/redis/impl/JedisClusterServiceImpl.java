package com.taotao.common.redis.impl;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;

import com.taotao.common.redis.RedisService;

/**
 * JedisCluster集群版实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月29日 下午5:46:26
 * @version 1.0
 */
public class JedisClusterServiceImpl implements RedisService {
	
	/** 注入JedisCluster */
	@Resource
	private JedisCluster jedisCluster;
	
	/**
	 * 设置键对应的值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	public String set(String key, String value){
		return jedisCluster.set(key, value);
	}
	/**
	 * 设置键对应的值、过期时间
	 * @param key 键
	 * @param value 值 
	 * @param seconds 过期时间（秒）
	 * @return 状态码
	 */
	public String setex(String key, String value, int seconds){
		return jedisCluster.setex(key, seconds, value);
	}
	/**
	 * 获取键获取值
	 * @param key 键
	 * @return 值 
	 */
	public String get(String key){
		return jedisCluster.get(key);
	}
	/**
	 * 设置键的过期时间
	 * @param key 键
	 * @return 状态码
	 */
	public Long expire(String key, int seconds){
		return jedisCluster.expire(key, seconds);
	}
	/**
	 * 删除指定键
	 * @param key 键
	 * @return 状态码
	 */
	public Long del(String key){
		return jedisCluster.del(key);
	}
	/**
	 * 获取指定的键的自增长的值
	 * @param key 键
	 * @return 自增长的值
	 */
	public Long incr(String key){
		return jedisCluster.incr(key);
	}
}