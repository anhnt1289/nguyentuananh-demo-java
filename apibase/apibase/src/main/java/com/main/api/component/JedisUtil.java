package com.main.api.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    /**
     * Store string key value pairs, permanently valid
     * @param key
     * @param value
     * @return
     * @author AnhNT
     * @date 2018 14 December 2003
     */
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.set(key, value);
        } catch (Exception e) {
            return "-1";
        } finally {
            // The business operation is completed and the connection is returned to the connection pool
//            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Get the specified Value according to the passed in key
     * @param key
     * @return
     * @author AnhNT
     * @date 2018 14 December 2003
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            return "-1";
        } finally {
            jedis.close();
        }
    }

    /**
     * Delete string key value pairs
     * @param key
     * @return
     * @author AnhNT
     * @date 2018 14 December 2003
     */
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.del(key);
        } catch (Exception e) {
            return -1L;
        } finally {
            jedis.close();
        }
    }
    /**
     * Verify whether the Key value exists
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            // Return connection
            jedis.close();
        }
    }
}
