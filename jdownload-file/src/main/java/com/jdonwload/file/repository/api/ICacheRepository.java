package com.jdonwload.file.repository.api;

import java.io.File;

public interface ICacheRepository {

    File get(String key);
    void add(String key, File value);

}
