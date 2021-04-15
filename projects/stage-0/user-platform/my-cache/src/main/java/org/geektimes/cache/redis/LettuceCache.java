package org.geektimes.cache.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.geektimes.cache.AbstractCache;
import org.geektimes.cache.ExpirableEntry;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.io.*;
import java.util.Set;


public class LettuceCache<K extends Serializable, V extends Serializable> extends AbstractCache {

    private StatefulRedisConnection connection = null;


    protected LettuceCache(CacheManager cacheManager, String cacheName, Configuration configuration, StatefulRedisConnection connection) {
        super(cacheManager, cacheName, configuration);
        this.connection = connection;
    }

    @Override
    protected boolean containsEntry(Object key) throws CacheException, ClassCastException {
        RedisCommands syncCommands = connection.sync();
        if(syncCommands.exists(key) > 0) return true;
        return false;
    }

    @Override
    protected ExpirableEntry<K, V> getEntry(Object key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        return getEntry(keyBytes);
    }

    protected ExpirableEntry<K, V> getEntry(byte[] keyBytes) throws CacheException, ClassCastException {
        RedisCommands syncCommands = connection.sync();
        byte[] valueBytes = serialize(syncCommands.get(keyBytes));
        return ExpirableEntry.of(deserialize(keyBytes), deserialize(valueBytes));
    }

    @Override
    protected void putEntry(ExpirableEntry entry) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(entry.getKey());
        byte[] valueBytes = serialize(entry.getValue());
        RedisCommands syncCommands = connection.sync();
        syncCommands.set(keyBytes, valueBytes);
    }

    @Override
    protected ExpirableEntry<K, V> removeEntry(Object key) throws CacheException, ClassCastException {
        byte[] keyBytes = serialize(key);
        ExpirableEntry<K, V> oldEntry = getEntry(keyBytes);
        RedisCommands syncCommands = connection.sync();
        syncCommands.del(keyBytes);
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
        this.connection.close();
    }

    // 是否可以抽象出一套序列化和反序列化的 API
    private byte[] serialize(Object value) throws CacheException {
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
        return bytes;
    }

    private <T> T deserialize(byte[] bytes) throws CacheException {
        if (bytes == null) {
            return null;
        }
        T value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
        return value;
    }
}
