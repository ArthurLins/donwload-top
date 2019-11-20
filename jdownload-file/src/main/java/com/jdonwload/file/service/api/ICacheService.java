package com.jdonwload.file.service.api;

import java.io.File;

public interface ICacheService {

    void addCache(String id, File file);

    File getFromCache(String id);

    boolean existsInCache(String id, long filesize);
}
