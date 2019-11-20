package com.jdonwload.file.service;

import com.jdonwload.file.repository.api.ICacheRepository;
import com.jdonwload.file.repository.factory.CacheRepositoryFactory;
import com.jdonwload.file.service.api.ICacheService;

import java.io.File;

public class CacheService implements ICacheService {

    private ICacheRepository repository;

    public CacheService(){
        repository = CacheRepositoryFactory.getRepository();
    }

    public void addCache(String id, File file) {
        repository.add(id, file);
    }

    public File getFromCache(String id) {
        return repository.get(id);
    }

    public boolean existsInCache(String id, long fileSize) {
        final File file = this.getFromCache(id);
        if (file == null)
            return false;
        return file.length() == fileSize;
    }



}
