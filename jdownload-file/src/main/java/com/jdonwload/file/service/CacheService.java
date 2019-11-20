package com.jdonwload.file.service;

import com.jdonwload.file.repository.api.ICacheRepository;
import com.jdonwload.file.repository.factory.CacheRepositoryFactory;
import com.jdonwload.file.service.api.ICacheService;

import java.io.File;
import java.nio.file.Path;

public class CacheService implements ICacheService {

    private ICacheRepository repository;

    public CacheService(){
        repository = CacheRepositoryFactory.getRepository();
    }

    public void addCache(Path path, File file) {
        repository.add(path.toString(), file);
    }

    public File getFromCache(Path path) {
        return repository.get(path.toString());
    }

    public boolean existsInCache(Path path, long fileSize) {
        final File file = this.getFromCache(path);
        if (file == null)
            return false;
        return file.length() == fileSize;
    }



}
