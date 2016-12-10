/**
 * 
 */
package com.taotao.sso.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.sso.dao.JedisClient;

import redis.clients.jedis.JedisCluster;

/**
 * @author dongdebao
 * @Date Dec 1, 2016 1:42:30 PM
 * @desc 
 */
public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public Long hdel(String hkey, String key) {
		return jedisCluster.hdel(key, key);
	}

	@Override
	public Long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}
	


}
