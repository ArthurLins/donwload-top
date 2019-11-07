package me.arhturlins.jdonwload.cahce.service;

import me.arhturlins.jdonwload.cahce.repository.api.IFileStorageRepository;
import me.arhturlins.jdonwload.cahce.repository.factory.FileStorageRepositoryFactory;
import me.arhturlins.jdonwload.cahce.service.api.IFileStorageService;

import java.io.File;
import java.nio.file.Path;

public class FileStorageService implements IFileStorageService {

    private IFileStorageRepository repository;

    public FileStorageService(){
        this.repository = FileStorageRepositoryFactory.getRepository();
    }

    @Override
    public void addToFs(Path path, byte[] file) {
        repository.add(path, file);
    }

    @Override
    public File getFromFS(Path path) {
        return repository.get(path);
    }

    @Override
    public boolean existsInFS(Path path, long filesize) {
        return path.toFile().isFile() && path.toFile().length() == filesize;
    }
}
