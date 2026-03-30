package com.shawnix.codepadx.dto.response.course;

import com.shawnix.codepadx.dto.response.chapter.ChapterResponse;
import com.shawnix.codepadx.entity.enums.CourseStatus;
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
public class CourseDetailResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String thumbnailUrl;
    private CourseStatus status;
    private List<ChapterResponse> chapters;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
