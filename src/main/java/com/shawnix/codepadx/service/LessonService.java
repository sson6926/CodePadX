package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.lesson.CreateLessonRequest;
import com.shawnix.codepadx.dto.request.lesson.UpdateLessonRequest;
import com.shawnix.codepadx.dto.response.lesson.LessonResponse;
import com.shawnix.codepadx.entity.Chapter;
import com.shawnix.codepadx.entity.Lesson;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.ChapterRepository;
import com.shawnix.codepadx.repository.LessonRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;

    public LessonService(LessonRepository lessonRepository, ChapterRepository chapterRepository) {
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
    }

    public LessonResponse createLesson(Long courseId, Long chapterId, CreateLessonRequest request) {
        Chapter chapter = findChapterOrThrow(courseId, chapterId);
        Lesson lesson = new Lesson();
        lesson.setChapter(chapter);
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setOrderIndex(request.getOrderIndex());
        lesson.setVideoUrl(request.getVideoUrl());
        return LessonResponse.toResponse(lessonRepository.save(lesson));
    }

    public List<LessonResponse> getAllLessons(Long courseId, Long chapterId) {
        findChapterOrThrow(courseId, chapterId);
        return lessonRepository.findByChapterIdOrderByOrderIndexAsc(chapterId)
                .stream()
                .map(LessonResponse::toResponse)
                .toList();
    }

    public LessonResponse getLessonById(Long courseId, Long chapterId, Long lessonId) {
        findChapterOrThrow(courseId, chapterId);
        Lesson lesson = lessonRepository.findByIdAndChapterId(lessonId, chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        return LessonResponse.toResponse(lesson);
    }

    @Transactional
    public LessonResponse updateLesson(Long courseId, Long chapterId, Long lessonId, UpdateLessonRequest request) {
        try {
            findChapterOrThrow(courseId, chapterId);
            Lesson lesson = lessonRepository.findByIdAndChapterId(lessonId, chapterId)
                    .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
            if (!java.util.Objects.equals(request.getVersion(), lesson.getVersion())) {
                throw new AppException(ErrorCode.CONCURRENCY_CONFLICT);
            }
            lesson.setTitle(request.getTitle());
            lesson.setContent(request.getContent());
            lesson.setOrderIndex(request.getOrderIndex());
            lesson.setVideoUrl(request.getVideoUrl());
            Lesson saved = lessonRepository.saveAndFlush(lesson);
            return LessonResponse.toResponse(saved);
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException ex) {
            throw new AppException(ErrorCode.CONCURRENCY_CONFLICT);
        }
    }


    public void deleteLesson(Long courseId, Long chapterId, Long lessonId) {
        findChapterOrThrow(courseId, chapterId);
        Lesson lesson = lessonRepository.findByIdAndChapterId(lessonId, chapterId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        lessonRepository.delete(lesson);
    }

    private Chapter findChapterOrThrow(Long courseId, Long chapterId) {
        return chapterRepository.findByIdAndCourseId(chapterId, courseId)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
    }
}
