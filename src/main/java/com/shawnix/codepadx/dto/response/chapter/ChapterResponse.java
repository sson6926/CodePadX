package com.shawnix.codepadx.dto.response.chapter;

import com.shawnix.codepadx.dto.response.lesson.LessonShortResponse;
import com.shawnix.codepadx.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterResponse {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer orderIndex;
    private List<LessonShortResponse> lessons;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ChapterResponse toResponse(Chapter chapter) {
        return ChapterResponse.builder()
                .id(chapter.getId())
                .courseId(chapter.getCourse().getId())
                .title(chapter.getTitle())
                .description(chapter.getDescription())
                .lessons(
                chapter.getLessons() == null ? List.of() :
                        chapter.getLessons().stream()
                                .map(LessonShortResponse::toResponse)
                                .toList())
                .orderIndex(chapter.getOrderIndex())
                .createdAt(chapter.getCreatedAt())
                .updatedAt(chapter.getUpdatedAt())
                .build();
    }
}
