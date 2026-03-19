package com.shawnix.codepadx.dto.response.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeResponse {
    Long id;
    String title;
    String sourceCode;
    String input;
    Long languageId;
    Long userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
