package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.course.CreateCourseRequest;
import com.shawnix.codepadx.dto.request.course.UpdateCourseRequest;
import com.shawnix.codepadx.dto.response.PaginationResponse;
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
import com.shawnix.codepadx.specification.CourseSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

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

    public PaginationResponse<CourseResponse> searchCourses(
            String keyword,
            CourseStatus status,
            Double minPrice,
            Double maxPrice,
            int page,
            int size) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(
                normalizedPage,
                normalizedSize,
                Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Specification<Course> spec = CourseSpecification.build(keyword, status, minPrice, maxPrice);
        Page<Course> result = courseRepository.findAll(spec, pageable);
        return PaginationResponse.<CourseResponse>builder()
                .data(result.getContent().stream().map(CourseResponse::toResponse).toList())
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
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
