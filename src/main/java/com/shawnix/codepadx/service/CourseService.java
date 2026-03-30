package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.course.CreateCourseRequest;
import com.shawnix.codepadx.dto.request.course.UpdateCourseRequest;
import com.shawnix.codepadx.dto.response.chapter.ChapterResponse;
import com.shawnix.codepadx.dto.response.course.CourseDetailResponse;
import com.shawnix.codepadx.dto.response.course.CourseResponse;
import com.shawnix.codepadx.entity.Chapter;
import com.shawnix.codepadx.entity.Course;
import com.shawnix.codepadx.entity.Enrollment;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.entity.enums.CourseStatus;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.CourseRepository;
import com.shawnix.codepadx.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream().map(c -> CourseResponse.toResponse(c)).toList();
    }

    @Transactional
    public CourseDetailResponse getCourseDetailById(Long id) {
        Course course = courseRepository.findDetailById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        List<ChapterResponse> chapters = course.getChapterList().stream()
                .sorted(Comparator.comparing(Chapter::getOrderIndex))
                .map(ChapterResponse::toResponse)
                .toList();
        CourseDetailResponse response = CourseDetailResponse.toResponse(course);
        response.setChapters(chapters);
        return response;
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

    @Transactional
    public void enrollCourse(Long id) {
        User currentUser = getCurrentUser();
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        if (course.getStatus() != CourseStatus.PUBLISHED) {
            throw new AppException(ErrorCode.COURSE_NOT_PUBLISHED);
        }
        if (enrollmentRepository.existsByUserIdAndCourseId(currentUser.getId(), id)) {
            throw new AppException(ErrorCode.COURSE_ALREADY_ENROLLED);
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(currentUser);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return user;
    }

}
