package com.jdonwload.file.repository.impl.fs;

import com.jdonwload.file.repository.api.IFileStorageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class FileStorageRepository implements IFileStorageRepository {

    @Override
    public void add(Path path, byte[] content) throws IOException {
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(path.toString());
        fos.write(content);
        fos.flush();
        fos.close();

    }

    @Override
    public File get(Path path) {
        if (path.toFile().exists()) {
            return path.toFile();
        }
        return null;
    }

    @Override
    public boolean exists(Path path) {
        return get(path).exists();
    }
}
