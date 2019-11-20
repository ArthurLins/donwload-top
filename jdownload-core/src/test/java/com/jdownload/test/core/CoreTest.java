package com.jdownload.test.core;

import com.jdownload.JDownload;
import com.jdownload.pool.DownloadRequestBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("JDownload Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoreTest {

    private final static String testUrl = "http://187.108.112.23/testebw/file1.test";
    private final static Path path = Path.of("target");
    private final static Path path2 = Path.of("target", "test2");

    CoreTest() {
        Configurator.setRootLevel(Level.DEBUG);
        //Cria pastas de teste
        try {
            FileUtils.forceMkdir(path.toFile());
            FileUtils.forceMkdir(path2.toFile());
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Download de 1 arquivo")
    void uniqueRun() {
        CountDownLatch look = new CountDownLatch(1);
        JDownload jDownload = new JDownload(10);
        try {
            jDownload.download(new DownloadRequestBuilder().url(testUrl).path(path).onFinished((file -> {
                look.countDown();
                assertTrue(file.exists());
                System.out.println(file.getName() + " Received.");
            })));
            look.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } finally {
            jDownload.dispose();
        }
    }

    @Test
    @DisplayName("Download de 2 arquivos iguais no mesmo diretorio")
    void duplicatedFileRun() {
        CountDownLatch look = new CountDownLatch(2);
        JDownload jDownload = new JDownload(10);
        try {
            jDownload.download(new DownloadRequestBuilder().url(testUrl).path(path).onFinished((file -> {
                look.countDown();
                assertTrue(file.exists());
                System.out.println(file.getName() + " Received.");
            })));
            jDownload.download(new DownloadRequestBuilder().url(testUrl).path(path).onFinished((file -> {
                look.countDown();
                assertTrue(file.exists());
                System.out.println(file.getName() + " Received.");
            })));
            look.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } finally {
            jDownload.dispose();
        }

    }

    @Test
    @DisplayName("Download de 2 arquivos iguais diretorios diferentes")
    void duplicatedFilePathsDiffRun() {
        CountDownLatch look = new CountDownLatch(2);
        JDownload jDownload = new JDownload(10);
        try {
            jDownload.download(new DownloadRequestBuilder().url(testUrl).path(path).onFinished((file -> {
                look.countDown();
                assertTrue(file.exists());
                System.out.println(file.getAbsolutePath() + " Received.");
            })));
            jDownload.download(new DownloadRequestBuilder().url(testUrl).path(path2).onFinished((file -> {
                look.countDown();
                assertTrue(file.exists());
                System.out.println(file.getAbsolutePath() + " Received.");
            })));
            look.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } finally {
            jDownload.dispose();
        }

    }


}

