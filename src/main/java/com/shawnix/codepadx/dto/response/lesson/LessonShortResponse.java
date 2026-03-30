package com.shawnix.codepadx.dto.response.lesson;

import com.shawnix.codepadx.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonShortResponse {
    private Long id;
    private String title;
    private Integer orderIndex;

    public static LessonShortResponse toResponse(Lesson lesson) {
        return LessonShortResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .orderIndex(lesson.getOrderIndex())
                .build();
    }
}
