package org.geektimes.cache.serializer;

/**
 * 序列化反序列化异常
 *
 * @author wenhai
 * @date   2021/4/14
 */
public class SerializationException  extends RuntimeException {


    public SerializationException() {
        super();
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }

    protected SerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
