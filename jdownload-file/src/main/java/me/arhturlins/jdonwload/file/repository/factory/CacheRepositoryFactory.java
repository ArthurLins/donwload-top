package me.arhturlins.jdonwload.file.repository.factory;

import me.arhturlins.jdonwload.file.repository.api.ICacheRepository;
import me.arhturlins.jdonwload.file.repository.impl.runtime.CacheRepository;

public class CacheRepositoryFactory {

    private static ICacheRepository instance;

    public static ICacheRepository getRepository(){
        if (instance == null){
            instance = new CacheRepository();
        }
        return instance;
    }

}
