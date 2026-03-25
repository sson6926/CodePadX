package com.shawnix.codepadx.dto.request.course;

import com.shawnix.codepadx.entity.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCourseRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @PositiveOrZero(message = "Price must be greater than or equal to 0")
    private Double price;
    private String thumbnailUrl;
    private CourseStatus status;
}
