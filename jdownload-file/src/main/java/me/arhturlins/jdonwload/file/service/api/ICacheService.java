package me.arhturlins.jdonwload.file.service.api;

import java.io.File;
import java.net.URI;

public interface ICacheService {

    void addCache(URI uri, File file);
    File getFromCache(URI uri);
    boolean existsInCache(URI uri, long filesize);
}
