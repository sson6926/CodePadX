package com.shawnix.codepadx.service.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

public class TempFileService {
    public static String createTempFile(String dir, String content, String fileName) throws IOException {
        String filePath = dir + "/" + fileName;
        Files.writeString(Path.of(filePath), content);
        return filePath;
    }

    public static String createTempDir() throws IOException {
        String baseDir = "/tmp";
        String dir = baseDir + "/code-run-" + UUID.randomUUID();
        Files.createDirectories(Path.of(dir));
        return dir;
    }

    public static void deleteTempDir(String dir) throws IOException {

    }
}
