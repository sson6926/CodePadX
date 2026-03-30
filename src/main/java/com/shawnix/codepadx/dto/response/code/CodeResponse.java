package com.shawnix.codepadx.dto.response.code;

import com.shawnix.codepadx.entity.Code;
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

    public static CodeResponse toResponse(Code code) {
        return CodeResponse.builder()
                .id(code.getId())
                .title(code.getTitle())
                .sourceCode(code.getSourceCode())
                .input(code.getInput())
                .languageId(code.getLanguage().getId())
                .userId(code.getUser().getId())
                .createdAt(code.getCreatedAt())
                .updatedAt(code.getUpdatedAt())
                .build();
    }
}
