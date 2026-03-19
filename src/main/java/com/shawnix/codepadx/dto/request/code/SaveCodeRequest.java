package com.shawnix.codepadx.dto.request.code;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCodeRequest {
    String title;
    @NotBlank(message = "Source code cannot be blank")
    @NotNull(message = "Source code cannot be null")
    String sourceCode;
    String input;
    @NotNull
    int languageId;
}
