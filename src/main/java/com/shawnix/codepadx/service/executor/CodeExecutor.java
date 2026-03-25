package com.shawnix.codepadx.service.executor;

import com.shawnix.codepadx.dto.response.code.ExecuteCodeResponse;
import com.shawnix.codepadx.dto.response.code.LocalExecuteResponse;

public interface CodeExecutor {
    LocalExecuteResponse execute(String code, String input);
}
