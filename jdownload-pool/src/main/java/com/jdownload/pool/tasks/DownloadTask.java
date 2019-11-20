package com.jdownload.pool.tasks;

import com.jdonwload.file.FileFacade;
import com.jdownload.pool.DownloadRequest;
import com.jdownload.pool.DownloadThreadPool;
import com.jdownload.pool.util.MimeTypeUtil;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DownloadTask implements Runnable {

    private static final Logger logger = LogManager.getLogger(DownloadTask.class.getCanonicalName());

    private final DownloadThreadPool pool;
    private final FileFacade fileFacade;
    private DownloadRequest request;


    public DownloadTask(DownloadThreadPool pool, DownloadRequest request) {
        this.pool = pool;
        this.request = request;
        this.fileFacade = pool.getFileFacade();
    }

    private Headers getFileMetadata(OkHttpClient client) throws IOException {
        Request reqHead = new Request.Builder()
                .url(this.request.getUri().toURL())
                .head()
                .build();

        return client.newCall(reqHead)
                .execute()
                .headers();
    }

    private File downloadAndStoreFile(OkHttpClient client, Path path) throws IOException {
        Request reqBody = new Request.Builder()
                .url(this.request.getUri().toURL())
                .build();
        ResponseBody resBody = client.newCall(reqBody)
                .execute()
                .body();
        this.fileFacade.addToFs(path, resBody.bytes());
        File resultFile = path.toFile();
        this.fileFacade.addCache(this.request.getPath(), resultFile);
        return resultFile;
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();
        try {

            //Pega os metadados do arquivo
            final Headers fileHeaders = getFileMetadata(client);
            final long fileSize = Long.parseLong(fileHeaders.get("content-length"));
            final String fileExt = MimeTypeUtil.mimeToExt(fileHeaders.get("content-type"));


            //Gera o nome do arquivo.
            String filename = FilenameUtils.getBaseName(this.request.getUri().getPath());
            filename = filename + fileExt;
            Path path = this.request.getPath().resolve(filename);

            logger.debug("[" + Thread.currentThread().getName() + "] Start downloading from url: "
                    + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

            //Verifica se o arquivo existe no cache
            if (this.fileFacade.existsInCache(this.request.getPath(), fileSize)) {

                logger.debug("[" + Thread.currentThread().getName() + "][CACHE] Finished download from url: "
                        + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

                pool.notifyFinished(this.request, this.fileFacade.getFromCache(this.request.getPath()));
                return;
                //Verifica se o arquivo existe no FileSystem
            } else if (this.fileFacade.existsInFS(path, fileSize)) {
                logger.debug("[" + Thread.currentThread().getName() + "][FS] Finished download from url: "
                        + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

                File fsF = this.fileFacade.getFromFS(path);
                this.fileFacade.addCache(request.getPath(), fsF);
                pool.notifyFinished(request, fsF);
                return;
            }
            //Baixa o arquivo.
            File resultFile = downloadAndStoreFile(client, path);
            logger.debug("[" + Thread.currentThread().getName() + "] Finished download from url: "
                    + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

            this.pool.notifyFinished(request, resultFile);
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
