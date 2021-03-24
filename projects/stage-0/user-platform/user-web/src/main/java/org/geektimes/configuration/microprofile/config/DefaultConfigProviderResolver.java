package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultConfigProviderResolver extends ConfigProviderResolver {
    private Map<ClassLoader, Config> map = new ConcurrentHashMap<>();

    @Override
    public Config getConfig() {
        Config config = map.get(this.getClass().getClassLoader());
        if (config != null) {
            return config;
        }
        return getConfig(null);
    }

    @Override
    public Config getConfig(ClassLoader loader) {
        ClassLoader classLoader = loader;
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        ServiceLoader<Config> serviceLoader = ServiceLoader.load(Config.class, classLoader);
        Iterator<Config> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            // 获取 Config SPI 第一个实现
            return iterator.next();
        }
        throw new IllegalStateException("No Config implementation found!");
    }

    @Override
    public ConfigBuilder getBuilder() {
        return new DefaultConfigBuilder();
    }

    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {
        this.map.put(classLoader, config);
    }

    @Override
    public void releaseConfig(Config config) {

    }
}
