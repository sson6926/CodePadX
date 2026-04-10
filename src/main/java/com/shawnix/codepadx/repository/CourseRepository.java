package com.shawnix.codepadx.repository;

import com.shawnix.codepadx.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("""
    select distinct c
    from Course c
    left join fetch c.chapterList ch
    where c.id = :id
""")
    Optional<Course> findDetailById(@Param("id") Long id);
}
