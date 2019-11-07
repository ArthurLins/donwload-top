package me.arthurlins.jdownload.pool.tasks;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import me.arhturlins.jdonwload.cahce.service.CacheService;
import me.arthurlins.jdownload.pool.HttpThreadPool;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

public class DownloadTask implements Runnable {

    private HttpThreadPool pool;
    private URI uri;
    private Path path;

    public DownloadTask(HttpThreadPool pool, URI url, Path path){

        this.pool = pool;
        this.path = path;
        this.uri = url;
    }


    private long getExternalFilesize(OkHttpClient client) throws IOException {
        Request reqHead = new Request.Builder().url(uri.toURL()).head().build();
        return Long.parseLong(client.newCall(reqHead).execute().header("content-length"));
    }

    private File downloadAndStoreFile(OkHttpClient client, Path path) throws IOException {
        Request reqBody = new Request.Builder().url(uri.toURL()).build();
        ResponseBody resBody = client.newCall(reqBody).execute().body();
        pool.getFileFacade().addToFs(path, resBody.bytes());
        File resultFile = path.toFile();
        pool.getFileFacade().addCache(uri, resultFile);
        return resultFile;
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();

        try {
            String filename = uri.toString().substring( uri.toString().lastIndexOf('/')+1);
            path = path.resolve(filename);
            System.out.println(path);

            System.out.println("["+ Thread.currentThread().getName()+"] Start downloading from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());

            long filesize = getExternalFilesize(client);

            if (pool.getFileFacade().existsInCache(uri, filesize)){
                //Achou cache!
                System.out.println("["+ Thread.currentThread().getName()+"][CACHE] Finished download from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
                pool.notifyFinished(uri, pool.getFileFacade().getFromCache(uri));
                return;
            } else if (pool.getFileFacade().existsInFS(path, filesize)) {
                //Achou FS
                System.out.println("["+ Thread.currentThread().getName()+"][FS] Finished download from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
                File fsF = pool.getFileFacade().getFromFS(path);
                pool.getFileFacade().addCache(uri, fsF);
                pool.notifyFinished(uri,fsF);
                return;
            }
            //Baixa o arquivo.
            File resultFile = downloadAndStoreFile(client, path);
            System.out.println("["+ Thread.currentThread().getName()+"] Finished download from url: " + uri.toString() + " to directory: " + path.toAbsolutePath().toString());
            pool.notifyFinished(uri, resultFile);
        } catch (IOException e) {
            //Todo::
        }

    }
}
