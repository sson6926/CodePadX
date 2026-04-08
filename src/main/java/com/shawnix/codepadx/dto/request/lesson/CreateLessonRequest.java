package com.shawnix.codepadx.dto.request.lesson;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLessonRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    @NotNull(message = "Order index is required")
    @Min(value = 1, message = "Order index must be greater than or equal to 1")
    private Integer orderIndex;
    private String videoUrl;
}
