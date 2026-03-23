package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.ExecuteCodeResponse;
import com.shawnix.codepadx.dto.response.code.LocalExecuteResponse;

public class JavaExecutor implements CodeExecutor {
    private final String fileName = "Main.java";
    private final String inputFileName = "input.txt";

    @Override
    public ExecuteCodeResponse execute(String code, String input) {
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
        return ExecuteCodeResponse.builder()
                .sourceCode(code)
                .languageId(0)
                .input(input)
                .stdout(res.getStdout())
                .stderr(res.getStderr()).build();
    }

    public static void main(String[] args) {
        String code = """
                import java.util.Scanner;
                
                public class Main {
                    public static void main(String[] args) {
                        Scanner sc = new Scanner(System.in);
                        int a = sc.nextInt();
                        int b = sc.nextInt();
                        System.out.println(a + b);
                    }
                }
                """;
        String input = "3 5";
        JavaExecutor executor = new JavaExecutor();
        ExecuteCodeResponse res = executor.execute(code, input);
        System.out.println("stdout:");
        System.out.println(res.getStdout());
        System.out.println("stderr:");
        System.out.println(res.getStderr());
    }
}
