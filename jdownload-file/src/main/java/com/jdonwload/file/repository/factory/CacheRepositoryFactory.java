package com.jdonwload.file.repository.factory;

import com.jdonwload.file.repository.api.ICacheRepository;
import com.jdonwload.file.repository.impl.runtime.CacheRepository;

public class CacheRepositoryFactory {

    public static ICacheRepository getRepository(){
        return new CacheRepository();
    }

}
