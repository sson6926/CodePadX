package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.LocalExecuteResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DockerExecutor {

    public static LocalExecuteResponse exec(String... command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();

            BufferedReader output = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            BufferedReader error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );

            StringBuilder result_stdout = new StringBuilder();
            StringBuilder result_stderr = new StringBuilder();
            String line;

            while ((line = output.readLine()) != null) {
                result_stdout.append(line).append("\n");
            }

            while ((line = error.readLine()) != null) {
                result_stderr.append(line).append("\n");
            }

            process.waitFor();

            return LocalExecuteResponse.builder()
                    .stdout(result_stdout.toString().trim())
                    .stderr(result_stderr.toString().trim())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return LocalExecuteResponse.builder()
                    .stdout("")
                    .stderr(e.getMessage())
                    .build();
        }

    }
}
