package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByChapterIdOrderByOrderIndexAsc(Long chapterId);
    Optional<Lesson> findByIdAndChapterId(Long lessonId, Long chapterId);
}
