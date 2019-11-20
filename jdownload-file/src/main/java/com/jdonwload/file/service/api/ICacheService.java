package com.jdonwload.file.service.api;

import java.io.File;
import java.nio.file.Path;

public interface ICacheService {

    void addCache(Path path, File file);

    File getFromCache(Path uri);

    boolean existsInCache(Path uri, long filesize);
}
