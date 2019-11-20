package com.jdownload.pool;

import java.io.File;

public class DownloadedFile {

    private final File file;
    private boolean fromCache = false;
    private boolean fromFileSystem = false;
    private boolean fromInternet = false;
    private boolean fromMultiRequests = false;

    public DownloadedFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }

    public boolean isFromFileSystem() {
        return fromFileSystem;
    }

    public void setFromFileSystem(boolean fromFileSystem) {
        this.fromFileSystem = fromFileSystem;
    }

    public boolean isFromInternet() {
        return fromInternet;
    }

    public void setFromInternet(boolean fromInternet) {
        this.fromInternet = fromInternet;
    }

    public boolean isFromMultiRequests() {
        return fromMultiRequests;
    }

    public void setFromMultiRequests(boolean fromMultiRequests) {
        this.fromMultiRequests = fromMultiRequests;
    }
}
