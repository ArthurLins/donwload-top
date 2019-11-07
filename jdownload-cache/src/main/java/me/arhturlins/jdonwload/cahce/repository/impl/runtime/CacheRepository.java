package me.arhturlins.jdonwload.cahce.repository.impl.runtime;

import me.arhturlins.jdonwload.cahce.repository.api.ICacheRepository;


import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class CacheRepository implements ICacheRepository {

    private ConcurrentHashMap<String, File> cacheMap;

    public CacheRepository() {
        this.cacheMap = new ConcurrentHashMap<String, File>();
    }

    public File get(String key) {
        return cacheMap.get(key);
    }

    public void add(String key, File file) {
        cacheMap.put(key, file);
    }

    public void remove(String key) {
        cacheMap.get(key);
    }
}
