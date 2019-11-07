package me.arhturlins.jdonwload.cahce.repository.api;

import java.io.File;
import java.nio.file.Path;

public interface IFileStorageRepository {
    void add(Path path, byte[] content);
    File get(Path path);
    boolean exists(Path path);
}
