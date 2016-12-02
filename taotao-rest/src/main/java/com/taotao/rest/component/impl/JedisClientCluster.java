/**
 * 
 */
package com.taotao.rest.component.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.JedisCluster;

/**
 * @author dongdebao
 * @Date Dec 1, 2016 1:42:30 PM
 * @desc 
 */
public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;
	
	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#set(java.lang.String, java.lang.String)
	 */
	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#hset(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Long hset(String key, String item, String value) {
		return jedisCluster.hset(key, item, value);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#hget(java.lang.String, java.lang.String)
	 */
	@Override
	public String hget(String key, String item) {
		return jedisCluster.hget(key, item);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#incr(java.lang.String)
	 */
	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#decr(java.lang.String)
	 */
	@Override
	public Long decr(String key) {
		return jedisCluster.decr(key);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#expire(java.lang.String, int)
	 */
	@Override
	public Long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#ttl(java.lang.String)
	 */
	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	/* (non-Javadoc)
	 * @see com.taotao.rest.component.JedisClient#hdel(java.lang.String, java.lang.String)
	 */
	@Override
	public Long hdel(String key, String item) {
		return jedisCluster.hdel(key, item);
	}

	@Override
	public Long delete(String key) {
		return jedisCluster.del(key);
	}

}
