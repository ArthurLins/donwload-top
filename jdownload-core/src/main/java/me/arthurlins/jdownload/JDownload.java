package me.arthurlins.jdownload;


import me.arthurlins.jdownload.pool.DownloadThreadPool;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class JDownload {

    private DownloadThreadPool pool;

    public JDownload(){
        this.pool = new DownloadThreadPool();
    }

    public void download(String uri, String dir, Consumer<File> onFinished) throws URISyntaxException {
        URI uri1 = new URI(uri);
        Path path = Paths.get(dir);
        this.download(uri1,path,onFinished);
    }

    public void download(URI uri, Path dir, Consumer<File> onFinished){
        pool.submitDownload(uri,dir,onFinished);
    }

    public void stop() {
        pool.stop();
    }

}
