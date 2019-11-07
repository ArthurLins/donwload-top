package me.arhturlins.jdonwload.file.service;

import me.arhturlins.jdonwload.file.repository.api.ICacheRepository;
import me.arhturlins.jdonwload.file.repository.factory.CacheRepositoryFactory;
import me.arhturlins.jdonwload.file.service.api.ICacheService;

import java.io.File;
import java.net.URI;

public class CacheService implements ICacheService {

    private ICacheRepository repository;

    public CacheService(){
        repository = CacheRepositoryFactory.getRepository();
    }

    public void addCache(URI uri, File file){
        repository.add(uri.toString(), file);
    }

    public File getFromCache(URI uri){
        return repository.get(uri.toString());
    }

    public boolean existsInCache(URI uri, long filesize){
        final File file = this.getFromCache(uri);
        if (file == null)
            return false;
        return file.length() == filesize;
    }



}
