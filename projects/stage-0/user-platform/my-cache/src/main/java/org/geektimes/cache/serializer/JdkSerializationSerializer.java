package org.geektimes.cache.serializer;


import java.io.*;

/**
 * 使用 JDK 序列化对象
 *
 * @author wenhai
 * @date   2021/4/14
 */
public class JdkSerializationSerializer<T> implements Serializer<T>{

    /**
     * 把对象 t 序列化字节数组
     *
     * @param t 被序列化对象
     * @return 序列化后的字节数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(t);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
        return bytes;
    }


    /**
     * 把给定字节数组反序列化对象
     *
     * @param bytes 字节数组
     * @return 对象
     * @throws SerializationException
     */
    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws SerializationException {
        T t;
        try (ByteArrayInputStream outputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(outputStream)
        ) {
            t = (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return t;
    }
}
