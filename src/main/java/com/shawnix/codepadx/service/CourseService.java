package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.course.CreateCourseRequest;
import com.shawnix.codepadx.dto.request.course.UpdateCourseRequest;
import com.shawnix.codepadx.dto.response.course.CourseResponse;
import com.shawnix.codepadx.entity.Course;
import com.shawnix.codepadx.entity.enums.CourseStatus;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream().map(c -> CourseResponse.toResponse(c)).toList();
    }

    public CourseResponse createCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setStatus(request.getStatus() == null ? CourseStatus.DRAFT : request.getStatus());
        return CourseResponse.toResponse(courseRepository.save(course));
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        return CourseResponse.toResponse(course);
    }

    public CourseResponse updateCourse(Long id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setStatus(request.getStatus() == null ? course.getStatus() : request.getStatus());
        return CourseResponse.toResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }


}
