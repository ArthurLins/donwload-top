package me.arhturlins.jdonwload.cahce.repository.factory;

import me.arhturlins.jdonwload.cahce.repository.api.IFileStorageRepository;
import me.arhturlins.jdonwload.cahce.repository.impl.fs.FileStorageRepository;

public class FileStorageRepositoryFactory {

    public static IFileStorageRepository getRepository(){
        return new FileStorageRepository();
    }

}
