package com.shawnix.codepadx.dto.response.code;

import java.time.LocalDateTime;

public class CodeResponse {
    int id;
    String title;
    String sourceCode;
    String input;
    int languageId;
    int userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
