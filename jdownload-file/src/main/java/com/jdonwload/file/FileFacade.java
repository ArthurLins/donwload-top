package com.jdonwload.file;

import com.jdonwload.file.service.CacheService;
import com.jdonwload.file.service.FileStorageService;
import com.jdonwload.file.service.api.ICacheService;
import com.jdonwload.file.service.api.IFileStorageService;

import java.io.File;
import java.nio.file.Path;

public class FileFacade implements ICacheService, IFileStorageService {

    private IFileStorageService fileSystemService;
    private ICacheService cacheService;

    public FileFacade(){
        this.fileSystemService = new FileStorageService();
        this.cacheService = new CacheService();
    }

    @Override
    public void addCache(Path path, File file) {
        cacheService.addCache(path, file);
    }

    @Override
    public File getFromCache(Path path) {
        return cacheService.getFromCache(path);
    }

    @Override
    public boolean existsInCache(Path path, long fileSize) {
        return cacheService.existsInCache(path, fileSize);
    }

    @Override
    public void addToFs(Path path, byte[] file) {
        fileSystemService.addToFs(path,file);
    }

    @Override
    public File getFromFS(Path path) {
        return fileSystemService.getFromFS(path);
    }

    @Override
    public boolean existsInFS(Path path, long fileSize) {
        return fileSystemService.existsInFS(path, fileSize);
    }
}
