package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCourseIdOrderByOrderIndexAsc(Long courseId);
    Optional<Chapter> findByIdAndCourseId(Long id, Long courseId);
}
