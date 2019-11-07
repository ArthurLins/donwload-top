package me.arhturlins.jdonwload.file.repository.factory;

import me.arhturlins.jdonwload.file.repository.api.IFileStorageRepository;
import me.arhturlins.jdonwload.file.repository.impl.fs.FileStorageRepository;

public class FileStorageRepositoryFactory {

    public static IFileStorageRepository getRepository(){
        return new FileStorageRepository();
    }

}
