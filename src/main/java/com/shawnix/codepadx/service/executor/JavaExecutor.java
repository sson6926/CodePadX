package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.LocalExecuteResponse;

public class JavaExecutor implements CodeExecutor {
    private final String fileName = "Main.java";
    private final String inputFileName = "input.txt";

    @Override
    public LocalExecuteResponse execute(String code, String input) {
        String tmpDir = TempFileService.createTempDir();
        String codeFilePath = TempFileService.createTempFile(tmpDir, fileName, code);
        String inputFilePath = TempFileService.createTempFile(tmpDir, inputFileName, input);
        String uuid = tmpDir.substring(tmpDir.lastIndexOf("/") + 1);
        LocalExecuteResponse res = DockerExecutor.exec(
                "docker",
                "exec",
                "java_runner",
                "sh",
                "-c",
                "cd /app/" + uuid + " && javac Main.java && java Main < input.txt"
        );
        TempFileService.deleteTempDir(tmpDir);
        return res;
    }
}
