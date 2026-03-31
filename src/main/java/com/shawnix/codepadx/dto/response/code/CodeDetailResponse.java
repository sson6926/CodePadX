package com.shawnix.codepadx.dto.response.code;

import com.shawnix.codepadx.entity.Code;
import com.shawnix.codepadx.entity.Language;
import com.shawnix.codepadx.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDetailResponse {
    Long id;
    String title;
    String sourceCode;
    String input;

    Long languageId;
    String languageName;

    Long userId;
    String userName;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static CodeDetailResponse toResponse(Code code) {
        return CodeDetailResponse.builder()
                .id(code.getId())
                .title(code.getTitle())
                .sourceCode(code.getSourceCode())
                .input(code.getInput())
                .languageId(code.getLanguage().getId())
                .languageName(code.getLanguage().getName())
                .userId(code.getUser().getId())
                .userName(code.getUser().getUsername())
                .createdAt(code.getCreatedAt())
                .updatedAt(code.getUpdatedAt())
                .build();
    }

}
