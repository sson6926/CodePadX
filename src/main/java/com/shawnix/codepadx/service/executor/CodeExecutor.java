package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.ExecuteCodeResponse;

public interface CodeExecutor {
    ExecuteCodeResponse execute(String code, String input);
}
