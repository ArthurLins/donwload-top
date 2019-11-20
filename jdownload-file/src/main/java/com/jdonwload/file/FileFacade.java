package com.jdonwload.file;

import com.jdonwload.file.service.CacheService;
import com.jdonwload.file.service.FileStorageService;
import com.jdonwload.file.service.api.ICacheService;
import com.jdonwload.file.service.api.IFileStorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileFacade implements ICacheService, IFileStorageService {

    private IFileStorageService fileSystemService;
    private ICacheService cacheService;

    public FileFacade(){
        this.fileSystemService = new FileStorageService();
        this.cacheService = new CacheService();
    }

    @Override
    public void addCache(String id, File file) {
        cacheService.addCache(id, file);
    }

    @Override
    public File getFromCache(String id) {
        return cacheService.getFromCache(id);
    }

    @Override
    public boolean existsInCache(String id, long fileSize) {
        return cacheService.existsInCache(id, fileSize);
    }

    @Override
    public void addToFs(Path path, byte[] file) throws IOException {
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
