package com.shawnix.codepadx.service.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;

public class TempFileService {
    public static String createTempFile(String dir, String content, String fileName) {
        try {
            String filePath = dir + "/" + fileName;
            Files.writeString(Path.of(filePath), content);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file", e);
        }
    }

    public static String createTempDir() {
        try {
            String baseDir = "/tmp";
            String dir = baseDir + "/code-run-" + UUID.randomUUID();
            Files.createDirectories(Path.of(dir));
            return dir;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp directory", e);
        }

    }

    public static void deleteTempDir(String dir) throws IOException {

    }
}
