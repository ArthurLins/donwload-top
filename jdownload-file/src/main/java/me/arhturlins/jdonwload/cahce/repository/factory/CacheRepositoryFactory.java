package me.arhturlins.jdonwload.cahce.repository.factory;

import me.arhturlins.jdonwload.cahce.repository.api.ICacheRepository;
import me.arhturlins.jdonwload.cahce.repository.impl.runtime.CacheRepository;

public class CacheRepositoryFactory {

    private static ICacheRepository instance;

    public static ICacheRepository getRepository(){
        if (instance == null){
            instance = new CacheRepository();
        }
        return instance;
    }

}
