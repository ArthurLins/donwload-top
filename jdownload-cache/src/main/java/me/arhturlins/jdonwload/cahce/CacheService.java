package me.arhturlins.jdonwload.cahce;

import me.arhturlins.jdonwload.cahce.repository.api.ICacheRepository;
import me.arhturlins.jdonwload.cahce.repository.factory.CacheRepositoryFactory;

import java.io.File;
import java.net.URI;

public class CacheService {

    private ICacheRepository repository;

    public CacheService(){
        repository = CacheRepositoryFactory.getRepository();
    }

    public void add(URI uri, File file){
        repository.add(uri.toString(), file);
    }

    public File get(URI uri){
        return repository.get(uri.toString());
    }

    public boolean exists(URI uri, long filesize){
        final File file = this.get(uri);
        if (file == null)
            return false;
        return file.length() == filesize;
    }



}
