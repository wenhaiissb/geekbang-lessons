package org.geektimes.rest.core;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;

public class DefaultHeaderDelegate<T> implements RuntimeDelegate.HeaderDelegate<T> {

    private final Class parser;
    public <T> DefaultHeaderDelegate(Class<T> parser) {
        this.parser = parser;
    }



    @Override
    public T fromString(String value) {
        String[] split = value.split("/");
        if (MediaType.class.isAssignableFrom(parser)) {
            return (T) new MediaType(split[0], split[1]);
        }
        return null;
    }

    @Override
    public String toString(T value) {
        return null;
    }
}
