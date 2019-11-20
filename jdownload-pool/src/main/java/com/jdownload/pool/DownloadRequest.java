package com.jdownload.pool;

import com.jdownload.pool.util.MD5;

import java.net.URI;
import java.nio.file.Path;
import java.util.function.Consumer;

public class DownloadRequest {
    private Path path;
    private URI uri;
    private Consumer<DownloadedFile> fileConsumer;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Consumer<DownloadedFile> getFileConsumer() {
        return fileConsumer;
    }

    public void setFileConsumer(Consumer<DownloadedFile> fileConsumer) {
        this.fileConsumer = fileConsumer;
    }

    public String id() {
        return MD5.hash(this.getUri().toString() + this.getPath().toString());
    }

}
