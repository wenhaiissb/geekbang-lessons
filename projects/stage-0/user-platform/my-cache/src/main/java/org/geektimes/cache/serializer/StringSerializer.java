package org.geektimes.cache.serializer;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.*;

public class StringSerializer implements Serializer<String> {

    private final Charset charset;


    public StringSerializer() {
        this(UTF_8);
    }


    public StringSerializer(Charset charset) {
        this.charset = charset == null ? UTF_8 : charset;
    }

    /**
     * 把对象 t 序列化字节数组
     *
     * @param s 被序列化对象
     * @return 序列化后的字节数组
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(String s) throws SerializationException {
        return (s == null ? null : s.getBytes(charset));
    }

    /**
     * 把给定字节数组反序列化对象
     *
     * @param bytes 字节数组
     * @return 对象
     * @throws SerializationException
     */
    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
