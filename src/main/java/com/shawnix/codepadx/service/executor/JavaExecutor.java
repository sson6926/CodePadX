package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.ExecuteCodeResponse;

public class JavaExecutor implements CodeExecutor {
    private final String fileName = "Main.java";
    private final String inputFileName = "input.txt";
//    private final

    @Override
    public ExecuteCodeResponse execute(String code, String input) {
        String tmpDir = TempFileService.createTempDir();
        String codeFilePath = TempFileService.createTempFile(tmpDir, fileName, code);
        String inputFilePath = TempFileService.createTempFile(tmpDir, inputFileName, input);
        return null;
    }

    public static void main(String[] args) {
        String code = "import java.util.*;\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        String input = scanner.nextLine();\n" +
                "        System.out.println(\"Hello, \" + input + \"!\");\n" +
                "    }\n" +
                "}";
        String input = "World";
        String tmpDir = TempFileService.createTempDir();
        String codeFilePath = TempFileService.createTempFile(tmpDir, "Main.java", code);
        String inputFilePath = TempFileService.createTempFile(tmpDir, "input.txt", input);
    }
}
