package com.shawnix.codepadx.dto.response.code;

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
}
