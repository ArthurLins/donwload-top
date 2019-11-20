package com.jdonwload.file.repository.factory;

import com.jdonwload.file.repository.api.IFileStorageRepository;
import com.jdonwload.file.repository.impl.fs.FileStorageRepository;

public class FileStorageRepositoryFactory {

    public static IFileStorageRepository getRepository(){
        return new FileStorageRepository();
    }

}
