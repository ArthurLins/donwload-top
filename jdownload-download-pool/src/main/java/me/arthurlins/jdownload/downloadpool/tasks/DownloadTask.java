package me.arthurlins.jdownload.downloadpool.tasks;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import me.arhturlins.jdonwload.cahce.CacheService;
import me.arthurlins.jdownload.downloadpool.HttpThreadPool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class DownloadTask implements Runnable {

    private HttpThreadPool pool;
    private URI uri;
    private Path path;
    private CacheService cacheService;

    public DownloadTask(HttpThreadPool pool, URI url, Path path){
        this.pool = pool;
        this.path = path;
        this.uri = url;
        this.cacheService = pool.getCacheService();
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();

        try {
            String filename = uri.getPath();
            path = path.resolve(filename);
            System.out.println(path);

            System.out.println("["+ Thread.currentThread().getName()+"] Start downloading from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
            Request reqHead = new Request.Builder().url(uri.toURL()).head().build();
            final long filesize = Long.parseLong(client.newCall(reqHead).execute().header("content-length"));
            if (cacheService.exists(uri, filesize)){
                System.out.println("["+ Thread.currentThread().getName()+"][CACHE] Finished download from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
                pool.notifyFinished(uri, cacheService.get(uri));
                return;
            }
            Request reqBody = new Request.Builder().url(uri.toURL()).build();
            ResponseBody resBody = client.newCall(reqBody).execute().body();
            FileOutputStream fos = new FileOutputStream(path.toString());
            fos.write(resBody.bytes());
            fos.flush();
            fos.close();
            File resultFile = path.toFile();
            cacheService.add(uri, resultFile);
            System.out.println("["+ Thread.currentThread().getName()+"] Finished download from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
            pool.notifyFinished(uri, resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
