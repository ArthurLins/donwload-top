package com.jdonwload.test.file;


import com.jdonwload.file.FileFacade;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Storage Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileManipulationTest {

    private final byte[] fileContent = new byte[]{1, 0, 0, 0, 1};
    private final FileFacade fileFacade = new FileFacade();
    private final Path path = Path.of("target", "fs-test");
    private final String fileName = "testfile.test";


    @BeforeAll
    void init() {
        try {
            if (path.toFile().isDirectory()) {
                FileUtils.deleteDirectory(path.toFile());
            }
            FileUtils.forceMkdir(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(0)
    @DisplayName("FileSystem Test")
    void FSTest() throws IOException {
        Path path = this.path.resolve(fileName);

        System.out.println("Running FS");
        //Cria o arquivo.
        fileFacade.addToFs(path, this.fileContent);
        if (path.toFile().exists()) {
            //Verifica se o arquivo foi criado
            assertTrue(path.toFile().exists());
            //Verifica se o metodo responde da mesma maneira do anterior
            assertTrue(fileFacade.existsInFS(path, path.toFile().length()));

            byte[] content = new byte[0];
            try {
                content = new FileInputStream(path.toFile()).readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            //Valida se o arquivo que foi gravado está com o conteudo esperado.
            assertArrayEquals(content, fileContent);
        }

    }

    @Test
    @Order(1)
    @DisplayName("Cache Test")
    void cacheTest() {
        System.out.println("Running Cache");
        Path path = this.path.resolve(fileName);
        File file = fileFacade.getFromFS(path);
        if (file == null) {
            fail("Arquivo não existe");
        }
        //Adiciona arquivo exemplo no cache
        fileFacade.addCache(path.toString(), file);
        //Verifica se o arquivo exemplo está presente no cache.
        assertTrue(fileFacade.existsInCache(path.toString(), file.length()));
        //Verifica se o arquivo do cache é o mesmo arquivo do FS
        assertEquals(fileFacade.getFromCache(path.toString()), file);
    }


}
