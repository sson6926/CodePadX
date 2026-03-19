package com.shawnix.codepadx.dto.response.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    String sourceCode;
    String input;
    int languageId;
    String stdout;
    String stderr;
}
