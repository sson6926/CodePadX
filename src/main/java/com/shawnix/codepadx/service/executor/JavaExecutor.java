package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.ExecuteCodeResponse;

public class JavaExecutor implements CodeExecutor {
    private final String fileName = "Main.java";
    private final String inputFileName = "input.txt";

    @Override
    public ExecuteCodeResponse execute(String code, String input) {
        String tmpDir = TempFileService.createTempDir();
        String codeFilePath = TempFileService.createTempFile(tmpDir, fileName, code);
        String inputFilePath = TempFileService.createTempFile(tmpDir, inputFileName, input);
        return null;
    }
}
