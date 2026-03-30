package com.shawnix.codepadx.dto.request.code;

import com.shawnix.codepadx.entity.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCodeRequest {
    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    @Size(max = 255, message = "Title cannot be more than 255 characters")
    String title;
    @NotBlank(message = "Source code cannot be blank")
    @NotNull(message = "Source code cannot be null")
    @Size(max = 10000, message = "Source code cannot be more than 10000 characters")
    String sourceCode;
    @Size(max = 1000, message = "Input cannot be more than 1000 characters")
    String input;
    @NotNull(message = "Language ID is required")
    @Positive(message = "Language ID must be a positive number")
    int languageId;
}
