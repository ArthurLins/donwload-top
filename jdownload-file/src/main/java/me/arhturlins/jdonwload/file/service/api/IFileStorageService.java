package me.arhturlins.jdonwload.file.service.api;

import java.io.File;
import java.nio.file.Path;

public interface IFileStorageService {
    void addToFs(Path path, byte[] file);
    File getFromFS(Path path);
    boolean existsInFS(Path path, long filesize);
}
