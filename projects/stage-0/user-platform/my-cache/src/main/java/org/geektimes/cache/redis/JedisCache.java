package org.geektimes.cache.redis;

import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import org.geektimes.cache.serializer.Serializer;
import redis.clients.jedis.Jedis;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.util.Set;

public class JedisCache<K, V> extends AbstractCache<K, V> {

    private final Jedis jedis;

    public JedisCache(CacheManager cacheManager, String cacheName,
                      Configuration<K, V> configuration, Jedis jedis) {
        this(cacheManager, cacheName, configuration, jedis, null, null);
    }

    public JedisCache(CacheManager cacheManager, String cacheName,
                      Configuration<K, V> configuration, Jedis jedis,
                      Serializer keySerializer, Serializer valueSerializer) {
        super(cacheManager, cacheName, configuration, keySerializer, valueSerializer);
        this.jedis = jedis;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(key);
        return jedis.exists(keyBytes);
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(key);
        return getEntry(keyBytes);
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        byte[] valueBytes = jedis.get(keyBytes);
        return ExpirableEntry.of(deserializeKey(keyBytes), deserializeValue(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(entry.getKey());
        byte[] valueBytes = rawValue(entry.getValue());
        jedis.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        jedis.del(keyBytes);
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {
        // TODO
    }


    @Override
    protected Set<K> keySet() {
        // TODO
        return null;
    }

    @Override
    protected void doClose() {
        this.jedis.close();
    }


}
