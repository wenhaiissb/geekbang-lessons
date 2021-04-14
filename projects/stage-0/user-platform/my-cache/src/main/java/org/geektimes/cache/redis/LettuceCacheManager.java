package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * Letture
 *
 * @author wenhai
 * @date   2021/4/14
 */
public class LettuceCacheManager  extends AbstractCacheManager {

    private  RedisClient redisClient;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties) {
        super(cachingProvider, uri, classLoader, properties);
        this.redisClient = RedisClient.create(uri.toString().replace("lettuce","redis"));
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return new LettuceCache(this, cacheName, configuration, connect);
    }


    @Override
    protected void doClose() {
        redisClient.shutdown();
    }
}
