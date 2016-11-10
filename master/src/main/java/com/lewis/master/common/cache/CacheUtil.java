package com.lewis.master.common.cache;

import com.lewis.master.common.utils.JsonUtil;
import com.lewis.master.common.utils.MapUtil;
import com.lewis.master.common.utils.StringUtil;
import com.lewis.master.common.utils.beanUitl.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangminghua on 2016/11/10.
 */
@Repository
public class CacheUtil {

    private Logger logger = LoggerFactory.getLogger(CacheUtil.class);

    @Resource
    private JedisPool jedisPool;


    public String getCache(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            logger.error("getCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public void setCache(String key, Object value, int expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, expire, JsonUtil.toString(value));
        } catch (JedisConnectionException e) {
            logger.error("setCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public void setHashCache(String key, Object value) {
        Jedis jedis = null;
        try {
            Map<String, String> map = BeanUtil.bean2Map(value);
            jedis = jedisPool.getResource();
            if (MapUtil.isNotEmpty(map)) {
                jedis.hmset(key, map);
            }
        } catch (JedisConnectionException e) {
            logger.error("setCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
    }

    public void setHashFieldCache(String key,String field,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key,field,value);
        } catch (JedisConnectionException e) {
            logger.error("setCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
    }


    public <T> T hgetAll(String key, Class<T> type) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            if (MapUtil.isNotEmpty(map)) {
                return BeanUtil.map2Bean(map, type);
            }
        } catch (JedisConnectionException e) {
            logger.error("setCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
        return null;
    }

    public <T> T hgetField(String key,String field, Class<T> type) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.hget(key, field);
            if (type == String.class) {
                return (T)value;
            }else{
                if (StringUtil.isNotEmpty(value)) {
                    return JsonUtil.toBean(value,type);
                }
            }
        } catch (JedisConnectionException e) {
            logger.error("setCache({})", new Object[]{key});
            release(jedis);
            throw e;
        } finally {
            release(jedis);
        }
        return null;
    }


    /**
     * 连接释放
     *
     * @param jedis
     */
    private void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
