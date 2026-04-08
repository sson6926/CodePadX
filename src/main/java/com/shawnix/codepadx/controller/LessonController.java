package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.lesson.CreateLessonRequest;
import com.shawnix.codepadx.dto.request.lesson.UpdateLessonRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.lesson.LessonResponse;
import com.shawnix.codepadx.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/chapters/{chapterId}/lessons")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    ApiResponse<LessonResponse> createLesson(
            @PathVariable Long courseId,
            @PathVariable Long chapterId,
            @Valid @RequestBody CreateLessonRequest request) {
        return ApiResponse.<LessonResponse>builder()
                .message("Create lesson ok")
                .data(lessonService.createLesson(courseId, chapterId, request))
                .build();
    }

    @GetMapping
    ApiResponse<List<LessonResponse>> getAllLessons(
            @PathVariable Long courseId,
            @PathVariable Long chapterId) {
        return ApiResponse.<List<LessonResponse>>builder()
                .message("Get all lessons ok")
                .data(lessonService.getAllLessons(courseId, chapterId))
                .build();
    }

    @GetMapping("/{lessonId}")
    ApiResponse<LessonResponse> getLessonById(
            @PathVariable Long courseId,
            @PathVariable Long chapterId,
            @PathVariable Long lessonId) {
        return ApiResponse.<LessonResponse>builder()
                .message("Get lesson by id ok")
                .data(lessonService.getLessonById(courseId, chapterId, lessonId))
                .build();
    }

    @PutMapping("/{lessonId}")
    ApiResponse<LessonResponse> updateLesson(
            @PathVariable Long courseId,
            @PathVariable Long chapterId,
            @PathVariable Long lessonId,
            @Valid @RequestBody UpdateLessonRequest request) {
        return ApiResponse.<LessonResponse>builder()
                .message("Update lesson ok")
                .data(lessonService.updateLesson(courseId, chapterId, lessonId, request))
                .build();
    }

    @DeleteMapping("/{lessonId}")
    ApiResponse<Void> deleteLesson(
            @PathVariable Long courseId,
            @PathVariable Long chapterId,
            @PathVariable Long lessonId) {
        lessonService.deleteLesson(courseId, chapterId, lessonId);
        return ApiResponse.<Void>builder()
                .message("Delete lesson ok")
                .data(null)
                .build();
    }
}
