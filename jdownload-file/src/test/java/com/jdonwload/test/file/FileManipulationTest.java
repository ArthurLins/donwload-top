package com.jdonwload.test.file;


import com.jdonwload.file.FileFacade;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Storage Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileManipulationTest {

    private final byte[] fileContent = new byte[]{1, 0, 0, 0, 1};
    private FileFacade fileFacade;
    private Path path;


    FileManipulationTest() {
        this.fileFacade = new FileFacade();
        this.path = Path.of("target/temp-test");
    }

    @BeforeAll
    void init() {
        if (path.toFile().exists()) {
            try {
                Files.walk(path)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        path.toFile().mkdir();
        path = path.resolve("testfile.bin");
    }

    @Test
    @Order(0)
    @DisplayName("FileSystem Test")
    void FSTest() {
        System.out.println("Running FS");
        //Cria o arquivo.
        fileFacade.addToFs(this.path, this.fileContent);
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
        File file = fileFacade.getFromFS(path);
        if (file == null) {
            fail("Arquivo não existe");
        }
        //Adiciona arquivo exemplo no cache
        fileFacade.addCache(path, file);
        //Verifica se o arquivo exemplo está presente no cache.
        assertTrue(fileFacade.existsInCache(path, file.length()));
        //Verifica se o arquivo do cache é o mesmo arquivo do FS
        assertEquals(fileFacade.getFromCache(path), file);

    }

    @AfterAll
    void clear() {
        //Tenta deletar o arquivo gerado no teste (Não funciona no windows)
        try {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
