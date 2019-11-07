package me.arhturlins.jdonwload.cahce.repository.impl.fs;

import me.arhturlins.jdonwload.cahce.repository.api.IFileStorageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class FileStorageRepository implements IFileStorageRepository {

    @Override
    public void add(Path path, byte[] content) {
        try {
            FileOutputStream fos = new FileOutputStream(path.toString());
            fos.write(content);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            //Todo::
            e.printStackTrace();
        }
    }

    @Override
    public File get(Path path) {
        return new File(path.toUri());
    }

    @Override
    public boolean exists(Path path) {
        return get(path).exists();
    }
}
