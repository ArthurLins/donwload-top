package com.jdonwload.file.service.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface IFileStorageService {
    void addToFs(Path path, byte[] file) throws IOException;
    File getFromFS(Path path);
    boolean existsInFS(Path path, long filesize);
}
