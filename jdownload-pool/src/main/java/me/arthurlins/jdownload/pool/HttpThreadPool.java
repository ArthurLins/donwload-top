package me.arthurlins.jdownload.pool;

import me.arhturlins.jdonwload.cahce.FileFacade;
import me.arhturlins.jdonwload.cahce.service.CacheService;
import me.arthurlins.jdownload.pool.tasks.DownloadTask;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HttpThreadPool {

    private ThreadPoolExecutor executor;
    private FileFacade fileFacade;
    private ConcurrentHashMap<URI, List<Consumer<File>>> observers;

    public HttpThreadPool(){
        this.observers = new ConcurrentHashMap<>();
        this.executor = new ThreadPoolExecutor(5,10, Long.MAX_VALUE, TimeUnit.DAYS, new LinkedBlockingQueue<>());
        this.fileFacade = new FileFacade();
    }

    public void submitDownload(URI uri, Path path, Consumer<File> onFinished){
        if (observers.containsKey(uri)){
            observers.get(uri).add(onFinished);
            return;
        }
        List<Consumer<File>> newList = new ArrayList<>();
        newList.add(onFinished);
        observers.put(uri,newList);
        executor.submit(new DownloadTask(this, uri, path));
    }

    public void notifyFinished(URI uri, File resultFile){
        if (observers.containsKey(uri)){
            observers.get(uri).forEach((c)-> c.accept(resultFile));
            observers.remove(uri);
        }

    }

    public FileFacade getFileFacade() {
        return fileFacade;
    }
}
