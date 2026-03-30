package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.chapter.CreateChapterRequest;
import com.shawnix.codepadx.dto.request.chapter.UpdateChapterRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.chapter.ChapterResponse;
import com.shawnix.codepadx.service.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    @PostMapping
    ApiResponse<ChapterResponse> createChapter(
            @PathVariable Long courseId,
            @Valid @RequestBody CreateChapterRequest request) {
        return ApiResponse.<ChapterResponse>builder()
                .message("Create chapter ok")
                .data(chapterService.createChapter(courseId, request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ChapterResponse>> getAllChapters(@PathVariable Long courseId) {
        return ApiResponse.<List<ChapterResponse>>builder()
                .message("Get all chapters ok")
                .data(chapterService.getAllByCourse(courseId))
                .build();
    }

    @GetMapping("/{chapterId}")
    ApiResponse<ChapterResponse> getChapterById(
            @PathVariable Long courseId,
            @PathVariable Long chapterId) {
        return ApiResponse.<ChapterResponse>builder()
                .message("Get chapter by id ok")
                .data(chapterService.getById(courseId, chapterId))
                .build();
    }

    @PutMapping("/{chapterId}")
    ApiResponse<ChapterResponse> updateChapter(
            @PathVariable Long courseId,
            @PathVariable Long chapterId,
            @Valid @RequestBody UpdateChapterRequest request) {
        return ApiResponse.<ChapterResponse>builder()
                .message("Update chapter ok")
                .data(chapterService.updateChapter(courseId, chapterId, request))
                .build();
    }

    @DeleteMapping("/{chapterId}")
    ApiResponse<Void> deleteChapter(
            @PathVariable Long courseId,
            @PathVariable Long chapterId) {
        chapterService.deleteChapter(courseId, chapterId);
        return ApiResponse.<Void>builder()
                .message("Delete chapter ok")
                .data(null)
                .build();
    }
}
