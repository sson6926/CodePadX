package com.shawnix.codepadx.dto.response.lesson;

import com.shawnix.codepadx.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private Long chapterId;
    private String title;
    private String content;
    private Integer orderIndex;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    public static LessonResponse toResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .chapterId(lesson.getChapter().getId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .orderIndex(lesson.getOrderIndex())
                .videoUrl(lesson.getVideoUrl())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .version(lesson.getVersion())
                .build();
    }
}
