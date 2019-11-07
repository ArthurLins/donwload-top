package me.arhturlins.jdonwload.cahce.service.api;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public interface IFileStorageService {
    void addToFs(Path path, byte[] file);
    File getFromFS(Path path);
    boolean existsInFS(Path path, long filesize);
}
