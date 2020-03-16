package com.taotao.common.redis.impl;

import javax.annotation.Resource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.taotao.common.redis.RedisFunction;
import com.taotao.common.redis.RedisService;

/**
 * JedisPool单机版实现类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月29日 下午5:46:26
 * @version 1.0
 */
public class JedisPoolServiceImpl implements RedisService {
	
	/** 注入JedisPool连接池 */
	@Resource
	private JedisPool jedisPool; 
	
	/** 通用的执行方法 */
	private <T> T execute(RedisFunction<T> redisFunction){
		/** 获取Jedis */
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			return redisFunction.callback(jedis);
		}finally{
			if (jedis != null) jedis.close();
		}
	}
	/**
	 * 设置键对应的值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	public String set(final String key,final String value){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}
	/**
	 * 设置键对应的值、过期时间
	 * @param key 键
	 * @param value 值 
	 * @param seconds 过期时间（秒）
	 * @return 状态码
	 */
	public String setex(final String key,final String value,final int seconds){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}
	/**
	 * 获取键获取值
	 * @param key 键
	 * @return 值 
	 */
	public String get(final String key){
		return execute(new RedisFunction<String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	/**
	 * 设置键的过期时间
	 * @param key 键
	 * @return 状态码
	 */
	public Long expire(final String key,final int seconds){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}
	/**
	 * 删除指定键
	 * @param key 键
	 * @return 状态码
	 */
	public Long del(final String key){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}
	/**
	 * 获取指定的键的自增长的值
	 * @param key 键
	 * @return 自增长的值
	 */
	public Long incr(final String key){
		return execute(new RedisFunction<Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}
	
}
