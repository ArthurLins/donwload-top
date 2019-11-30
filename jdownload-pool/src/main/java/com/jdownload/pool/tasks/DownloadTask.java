package com.jdownload.pool.tasks;

import com.jdonwload.file.FileFacade;
import com.jdownload.pool.DownloadThreadPool;
import com.jdownload.pool.type.DownloadRequest;
import com.jdownload.pool.type.DownloadedFile;
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
        this.fileFacade.addCache(this.request.id(), resultFile);
        return resultFile;
    }

    public void run() {
        OkHttpClient client = new OkHttpClient();
        try {

            //Pega os metadados do arquivo
            final Headers fileHeaders = getFileMetadata(client);
            final long fileSize = Long.parseLong(fileHeaders.get("content-length"));

            String fileExt;
            if (FilenameUtils.getExtension(this.request.getUri().getPath()).equals("")) {
                fileExt = MimeTypeUtil.mimeToExt(fileHeaders.get("content-type"));
            } else {
                fileExt = "." + FilenameUtils.getExtension(this.request.getUri().getPath());
            }

            //Gera o nome do arquivo.
            String filename = FilenameUtils.getBaseName(this.request.getUri().getPath());
            filename = filename + fileExt;
            Path path = this.request.getPath().resolve(filename);

            //Verifica se o arquivo existe no cache
            if (this.fileFacade.existsInCache(this.request.id(), fileSize)) {

                logger.debug("Find on Cache file: " + path.toAbsolutePath().toString());

                DownloadedFile downFile = new DownloadedFile(this.fileFacade.getFromCache(this.request.id()));
                downFile.setFromCache(true);
                pool.notifyAll(this.request, downFile);
                return;

                //Verifica se o arquivo existe no FileSystem
            } else if (this.fileFacade.existsInFS(path, fileSize)) {
                logger.debug("Find on FileSystem file: " + path.toAbsolutePath().toString());
                File fsF = this.fileFacade.getFromFS(path);
                this.fileFacade.addCache(request.id(), fsF);
                DownloadedFile downFile = new DownloadedFile(fsF);
                downFile.setFromFileSystem(true);
                pool.notifyAll(request, downFile);
                return;
            }
            logger.debug("Start download from url: "
                    + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

            //Baixa o arquivo.
            File resultFile = downloadAndStoreFile(client, path);
            logger.debug("Finished download from url: "
                    + this.request.getUri().toString() + " to directory: " + path.toAbsolutePath().toString());

            DownloadedFile downFile = new DownloadedFile(resultFile);
            downFile.setFromInternet(true);
            this.pool.notifyAll(request, downFile);
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
