package com.taotao.common.redis;
/**
 * Redis服务接口类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月29日 下午5:40:08
 * @version 1.0
 */
public interface RedisService {
	/**
	 * 设置键对应的值
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	String set(String key, String value);
	/**
	 * 设置键对应的值、过期时间
	 * @param key 键
	 * @param value 值 
	 * @param seconds 过期时间（秒）
	 * @return 状态码
	 */
	String setex(String key, String value, int seconds);
	/**
	 * 获取键获取值
	 * @param key 键
	 * @return 值 
	 */
	String get(String key);
	/**
	 * 设置键的过期时间
	 * @param key 键
	 * @return 状态码
	 */
	Long expire(String key,int seconds);
	/**
	 * 删除指定键
	 * @param key 键
	 * @return 状态码
	 */
	Long del(String key);
	/**
	 * 获取指定的键的自增长的值
	 * @param key 键
	 * @return 自增长的值
	 */
	Long incr(String key);
}