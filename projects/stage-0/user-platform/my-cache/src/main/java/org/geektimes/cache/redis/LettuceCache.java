package org.geektimes.cache.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;
import org.geektimes.cache.serializer.Serializer;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.util.Set;

public class LettuceCache<K, V> extends AbstractCache<K, V> {
    private final StatefulRedisConnection<String, String> lettuce;

    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, StatefulRedisConnection<String, String> lettuce) {
        this(cacheManager, cacheName, configuration, lettuce, null, null);
    }

    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration<K, V> configuration, StatefulRedisConnection<String, String> lettuce, Serializer keySerializer, Serializer valueSerializer) {
        super(cacheManager, cacheName, configuration, keySerializer, valueSerializer);
        this.lettuce = lettuce;
    }

    @Override
    protected boolean containsEntry(K key) throws CacheException, ClassCastException {
        byte[] bytes = rawKey(key);
        return lettuce.sync().get(toHexString(bytes)) != null;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(K key) throws CacheException, ClassCastException {
        byte[] bytes = rawKey(key);
        return getEntry(bytes);
    }

    @Override
    protected void putEntry(ExpirableEntry<K, V> entry) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(entry.getKey());
        byte[] valueBytes = rawValue(entry.getValue());
        lettuce.sync().set(toHexString(keyBytes), toHexString(valueBytes));
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(K key) throws CacheException, ClassCastException {
        byte[] keyBytes = rawKey(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        lettuce.sync().del(toHexString(keyBytes));
        return oldEntry;
    }

    @Override
    protected void clearEntries() throws CacheException {

    }

    @Override
    protected Set<K> keySet() {
        return null;
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        String value = lettuce.sync().get(toHexString(keyBytes));
        return ExpirableEntry.of(deserializeKey(keyBytes), deserializeValue(toByteArray(value)));
    }

    private static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
    private static byte[] toByteArray(String hexString) {
        if (hexString == null || hexString.length() < 1) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

}
