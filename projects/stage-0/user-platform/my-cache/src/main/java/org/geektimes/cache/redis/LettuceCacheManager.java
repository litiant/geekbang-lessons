package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.geektimes.cache.AbstractCacheManager;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;


/**
 * {@link javax.cache.CacheManager} based on Lettuce
 */
public class LettuceCacheManager extends AbstractCacheManager {

    private RedisClient redisClient = null;
    private GenericObjectPool<StatefulRedisConnection<String, String>> lettucePool;

    public LettuceCacheManager(CachingProvider cachingProvider, URI uri, ClassLoader classLoader, Properties properties, StatefulRedisConnection connection) {
        super(cachingProvider, uri, classLoader, properties);
        redisClient = RedisClient.create(RedisURI.create(uri));
        this.lettucePool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), new GenericObjectPoolConfig());
    }

    @Override
    protected <K, V, C extends Configuration<K, V>> Cache doCreateCache(String cacheName, C configuration) {
        try (StatefulRedisConnection<String, String> connection = lettucePool.borrowObject()){
            return new LettuceCache<>(this, cacheName, configuration, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doClose() {
        lettucePool.close();
        redisClient.shutdown();
    }
}
