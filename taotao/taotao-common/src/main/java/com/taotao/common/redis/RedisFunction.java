package com.taotao.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Redis函数类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月29日 下午5:54:41
 * @version 1.0
 */
public interface RedisFunction<T> {
	
	/** 回调方法，处理不一样的业务 */
	T callback(Jedis jedis);
}
