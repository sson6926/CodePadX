package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.course.CreateCourseRequest;
import com.shawnix.codepadx.dto.request.course.UpdateCourseRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.course.CourseDetailResponse;
import com.shawnix.codepadx.dto.response.course.CourseResponse;
import com.shawnix.codepadx.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    ApiResponse<CourseResponse> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        return ApiResponse.<CourseResponse>builder()
                .data(courseService.createCourse(request))
                .message("Create course ok")
                .build();
    }

    @GetMapping
    ApiResponse<List<CourseResponse>> getAllCourses() {
        return ApiResponse.<List<CourseResponse>>builder()
                .data(courseService.getAllCourses())
                .message("Get all courses ok")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CourseResponse> getCourseById(@PathVariable Long id) {
        return ApiResponse.<CourseResponse>builder()
                .data(courseService.getCourseById(id))
                .message("Get course by id ok")
                .build();
    }

    @GetMapping("/{id}/detail")
    ApiResponse<CourseDetailResponse> getCourseDetailById(@PathVariable Long id) {
        return ApiResponse.<CourseDetailResponse>builder()
                .data(courseService.getCourseDetailById(id))
                .message("Get course detail by id ok")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody UpdateCourseRequest request) {
        return ApiResponse.<CourseResponse>builder()
                .data(courseService.updateCourse(id, request))
                .message("Update course ok")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ApiResponse.<Void>builder()
                .message("Delete course ok")
                .data(null)
                .build();
    }

    @PostMapping("/{id}/enroll")
    ApiResponse<Void> enrollCourse(@PathVariable Long id) {
        courseService.enrollCourse(id);
        return ApiResponse.<Void>builder()
                .message("Enroll course ok")
                .data(null)
                .build();
    }
}
