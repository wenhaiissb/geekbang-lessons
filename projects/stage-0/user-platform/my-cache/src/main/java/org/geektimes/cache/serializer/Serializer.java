package org.geektimes.cache.serializer;

/**
 * 序列化顶级接口
 *
 * @author wenhai
 * @date   2021/4/14
 */
public interface Serializer<T> {

    /**
     * 把对象 t 序列化字节数组
     * @param t  被序列化对象
     * @return   序列化后的字节数组
     * @throws SerializationException
     */
    byte[] serialize(T t) throws SerializationException;

    /**
     * 把给定字节数组反序列化对象
     *
     * @param bytes   字节数组
     * @return        对象
     * @throws SerializationException
     */
    T deserialize(byte[] bytes) throws SerializationException;
}
