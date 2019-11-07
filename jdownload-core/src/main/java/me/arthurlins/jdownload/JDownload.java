package me.arthurlins.jdownload;

import me.arhturlins.jdonwload.cahce.CacheService;
import me.arthurlins.jdownload.downloadpool.HttpThreadPool;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class JDownload {

    private HttpThreadPool pool;

    public JDownload(){
        this.pool = new HttpThreadPool();
    }

    public void download(String uri, String dir, Consumer<File> onFinished) throws URISyntaxException {
        URI uri1 = new URI(uri);
        Path path = Paths.get(dir);
        this.download(uri1,path,onFinished);
    }

    public void download(URI uri, Path dir, Consumer<File> onFinished){
        pool.submitDownload(uri,dir,onFinished);
    }

    public static void main(String[] args) {
        JDownload jd = new JDownload();

        for (int i =0; i < 10000; i++) {
            try {
                jd.download("http://212.183.159.230/5MB.zip", "./hello/", (file -> {
                    System.out.println("recebido!");
                }));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
