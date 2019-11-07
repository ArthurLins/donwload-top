package me.arhturlins.jdonwload.file;

import me.arhturlins.jdonwload.file.service.CacheService;
import me.arhturlins.jdonwload.file.service.FileStorageService;
import me.arhturlins.jdonwload.file.service.api.ICacheService;
import me.arhturlins.jdonwload.file.service.api.IFileStorageService;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public class FileFacade implements ICacheService, IFileStorageService {

    private IFileStorageService fileSystemService;
    private ICacheService cacheService;

    public FileFacade(){
        this.fileSystemService = new FileStorageService();
        this.cacheService = new CacheService();
    }

    @Override
    public void addCache(URI uri, File file) {
        cacheService.addCache(uri,file);
    }

    @Override
    public File getFromCache(URI uri) {
        return cacheService.getFromCache(uri);
    }

    @Override
    public boolean existsInCache(URI uri, long filesize) {
        return cacheService.existsInCache(uri, filesize);
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
    public boolean existsInFS(Path path, long filesize) {
        return fileSystemService.existsInFS(path, filesize);
    }
}
