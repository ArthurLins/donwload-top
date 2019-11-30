package com.jdownload.test.core;

import com.jdownload.JDownload;
import com.jdownload.pool.type.builder.DownloadRequestBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("JDownload Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoreTest {

    /**
     * Deletar todos os arquivos gerados no final da execução
     */
    private final static boolean DELETE_RESULT = false;

    private final static Path path = Path.of("target", "test1");
    private final static Path path2 = Path.of("target", "test2");

    private final static List<String> urls = new ArrayList<>() {{
        add("https://i.pinimg.com/originals/ab/21/d5/ab21d5b71ba7b3b74947c3e5656c6aee.jpg");
        add("https://i.pinimg.com/originals/21/be/d0/21bed0308cc5ec238364af65016a996b.jpg");
        add("https://media2.giphy.com/media/BnWs1poLcXzEY/giphy.gif");
        add("https://uploads.metropoles.com/wp-content/uploads/2019/01/19112146/Screenshot_2588.jpg");
        add("https://i.pinimg.com/236x/9a/08/45/9a08455689e8e94b044ae0d8d2295ad0.jpg");
        add("https://www.lugardopet.com/wp-content/uploads/2019/02/cachorros-fofos-ra%C3%A7as-para-se-apaixonar-pug-878x1024.jpg");
        add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQio4BVq_53dDvgyoRhev2XJkqWoud976UGlFgYt0UTNlrStXSAWg&s");
        add("https://i.ytimg.com/vi/5OHJe4hOfRo/hqdefault.jpg");
        add("https://i.ytimg.com/vi/5OHJe4hOfRo/hqdefault.jpg");
        add("https://i.ytimg.com/vi/5OHJe4hOfRo/hqdefault.jpg");
        add("https://thumbs.gfycat.com/IndelibleBriefBlackbear-mobile.jpg");
        add("https://public-rf-upload.minhawebradio.net/13292/news/22f88e3ad7aad13aabf422f4319d398e.jpg");
        add("https://i.pinimg.com/736x/b8/c4/49/b8c449121a16bae7cab19310bc948aad.jpg");
        add("https://link.estadao.com.br/blogs/nhom/wp-content/uploads/sites/374/2011/04/passaro-plumoso.jpg");
        add("https://i.ytimg.com/vi/5OHJe4hOfRo/hqdefault.jpg");
        add("https://media2.giphy.com/media/BnWs1poLcXzEY/giphy.gif");
        add("https://i.imgur.com/9YlIGIo.mp4");
        add("http://www1.pucminas.br/documentos/artigo-abnt-templente-finalissimo.pdf");
        add("http://www.cnen.gov.br/images/CIN/Cursos/Curso_CIN_Estruturacao_Artigo_Cientifico_Maio2017.pdf");
        add("http://www.alexandrecordel.com.br/fbv/Aula_Banco_Dados_Completo.pptx");
        add("http://www.alexandrecordel.com.br/fbv/Aula_SQL_Joins.pptx");
        add("http://www.alexandrecordel.com.br/fbv/projeto.crud.rar");
        add("https://web.integrees.net/img/button-home-turmas.png");
        add("https://web.integrees.net/img/grupos.png");
        add("https://web.integrees.net/img/button-home-calendario.png");
        add("https://web.integrees.net/img/pex.png");
        add("https://web.integrees.net/img/icone_integrees_web_store.png");
        add("https://web.integrees.net/img/ead.png");
        add("https://i.imgur.com/CjRnyHb.mp4");
        add("https://i.imgur.com/9YlIGIo.mp4");
        add("https://i.imgur.com/HPEaqts.mp4");
        add("https://i.imgur.com/VqIk5D0.mp4");
        add("https://media.vocaroo.com/mp3/oqsE8SitHJZ");
        add("https://media.vocaroo.com/mp3/oqsE8SitHJZ");
    }};


    @BeforeAll
    void init() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    @BeforeEach
    void createDirs() {
        try {
            FileUtils.deleteDirectory(path.toFile());
            FileUtils.deleteDirectory(path2.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.forceMkdir(path.toFile());
            FileUtils.forceMkdir(path2.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void clearDirs() {
        if (!DELETE_RESULT) {
            return;
        }
        try {
            FileUtils.deleteDirectory(path.toFile());
            FileUtils.deleteDirectory(path2.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Download de 1 arquivo")
    void uniqueRun() {
        CountDownLatch look = new CountDownLatch(1);
        JDownload jDownload = new JDownload(10);
        AtomicBoolean pass = new AtomicBoolean(true);
        try {
            jDownload.download(new DownloadRequestBuilder().url(urls.get(0)).path(path).onFinished((dfile -> {
                look.countDown();
                if (pass.get() && dfile.getFile().exists() && dfile.isFromInternet()) {
                    pass.set(true);
                } else {
                    pass.set(false);
                }
            })));
            look.await();
            assertTrue(pass.get());
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
        AtomicBoolean pass = new AtomicBoolean(true);

        try {
            jDownload.download(new DownloadRequestBuilder().url(urls.get(0)).path(path).onFinished((dfile -> {
                look.countDown();
                if (pass.get() && dfile.getFile().exists() && dfile.isFromInternet() && dfile.isFromMultiRequests()) {
                    pass.set(true);
                } else {
                    pass.set(false);
                }

            })));
            jDownload.download(new DownloadRequestBuilder().url(urls.get(0)).path(path).onFinished((dfile -> {
                look.countDown();
                if (pass.get() && dfile.getFile().exists() && dfile.isFromInternet() && dfile.isFromMultiRequests()) {
                    pass.set(true);
                } else {
                    pass.set(false);
                }
            })));
            look.await();
            assertTrue(pass.get());
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
        AtomicBoolean pass = new AtomicBoolean(true);
        try {
            jDownload.download(new DownloadRequestBuilder().url(urls.get(0)).path(path).onFinished((dfile -> {
                look.countDown();
                if (pass.get() && dfile.getFile().exists() && dfile.isFromInternet()) {
                    pass.set(true);
                } else {
                    pass.set(false);
                }
            })));
            jDownload.download(new DownloadRequestBuilder().url(urls.get(0)).path(path2).onFinished((dfile -> {
                look.countDown();
                if (pass.get() && dfile.getFile().exists() && dfile.isFromInternet()) {
                    pass.set(true);
                } else {
                    pass.set(false);
                }
            })));
            look.await();
            assertTrue(pass.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        } finally {
            jDownload.dispose();
        }
    }

    @Test
    @DisplayName("Download da intrernet, consulta de cache e filesystem")
    void massiveTest() {
        CountDownLatch look = new CountDownLatch(urls.size());
        JDownload jDownload = new JDownload(10);

        AtomicBoolean b = new AtomicBoolean(true);
        for (String uri : urls) {
            jDownload.download(new DownloadRequestBuilder().path(path).url(uri).onFinished(dfile -> {
                assertTrue(dfile.getFile().exists());
                if (b.get()) {
                    b.set(dfile.isFromInternet());
                }
                look.countDown();
            }));
        }
        try {
            look.await();
            assertTrue(b.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Cache test
        System.out.println("################## CACHE ##################");
        System.out.println("################## CACHE ##################");
        System.out.println("################## CACHE ##################");
        CountDownLatch look2 = new CountDownLatch(urls.size());
        AtomicBoolean b2 = new AtomicBoolean(true);
        for (String uri : urls) {
            jDownload.download(new DownloadRequestBuilder().path(path).url(uri).onFinished(dfile -> {
                assertTrue(dfile.getFile().exists());
                if (b2.get()) {
                    b2.set(dfile.isFromCache());
                }
                look2.countDown();
            }));
        }
        try {
            look2.await();
            assertTrue(b2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jDownload.dispose();
        jDownload = new JDownload(10);
        //FS
        System.out.println("################## FS ##################");
        System.out.println("################## FS ##################");
        System.out.println("################## FS ##################");

        CountDownLatch look3 = new CountDownLatch(urls.size());
        AtomicBoolean b3 = new AtomicBoolean(true);
        for (String uri : urls) {
            jDownload.download(new DownloadRequestBuilder().path(path).url(uri).onFinished(dfile -> {
                assertTrue(dfile.getFile().exists());
                if (b3.get()) {
                    b3.set(dfile.isFromFileSystem());
                }
                look3.countDown();
            }));
        }
        try {
            look3.await();
            assertTrue(b3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}

