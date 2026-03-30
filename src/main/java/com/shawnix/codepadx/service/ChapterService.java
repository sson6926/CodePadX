package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.chapter.CreateChapterRequest;
import com.shawnix.codepadx.dto.request.chapter.UpdateChapterRequest;
import com.shawnix.codepadx.dto.response.chapter.ChapterResponse;
import com.shawnix.codepadx.entity.Chapter;
import com.shawnix.codepadx.entity.Course;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.ChapterRepository;
import com.shawnix.codepadx.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;

    public ChapterService(ChapterRepository chapterRepository, CourseRepository courseRepository) {
        this.chapterRepository = chapterRepository;
        this.courseRepository = courseRepository;
    }

    public ChapterResponse createChapter(Long courseId, CreateChapterRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        Chapter chapter = new Chapter();
        chapter.setTitle(request.getTitle());
        chapter.setDescription(request.getDescription());
        chapter.setOrderIndex(request.getOrderIndex());
        course.addChapter(chapter);
        courseRepository.save(course);
        return ChapterResponse.toResponse(chapter);
    }

    public List<ChapterResponse> getAllByCourse(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        return chapterRepository.findByCourseIdOrderByOrderIndexAsc(courseId)
                .stream()
                .map(ChapterResponse::toResponse)
                .toList();
    }

    public ChapterResponse getById(Long courseId, Long chapterId) {
        Chapter chapter = findChapterOrThrow(courseId, chapterId);
        return ChapterResponse.toResponse(chapter);
    }

    public ChapterResponse updateChapter(Long courseId, Long chapterId, UpdateChapterRequest request) {
        Chapter chapter = findChapterOrThrow(courseId, chapterId);
        chapter.setTitle(request.getTitle());
        chapter.setDescription(request.getDescription());
        chapter.setOrderIndex(request.getOrderIndex());
        return ChapterResponse.toResponse(chapterRepository.save(chapter));
    }

    public void deleteChapter(Long courseId, Long chapterId) {
        Chapter chapter = findChapterOrThrow(courseId, chapterId);
        chapterRepository.delete(chapter);
    }

    private Course findCourseOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
    }

    private Chapter findChapterOrThrow(Long courseId, Long chapterId) {
        return chapterRepository.findByIdAndCourseId(chapterId, courseId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
    }
}
