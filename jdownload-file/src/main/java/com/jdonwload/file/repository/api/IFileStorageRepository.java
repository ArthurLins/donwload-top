package com.jdonwload.file.repository.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface IFileStorageRepository {
    void add(Path path, byte[] content) throws IOException;
    File get(Path path);
    boolean exists(Path path);
}
