package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
