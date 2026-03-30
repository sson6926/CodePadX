package com.shawnix.codepadx.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(401, "User existed"),
    USER_NOT_FOUND(404, "User not found"),
    CODE_NOT_FOUND(404, "Code not found"),
    COURSE_NOT_FOUND(404, "Course not found"),
    CHAPTER_NOT_FOUND(404, "Chapter not found"),
    LESSON_NOT_FOUND(404, "Lesson not found"),
    LANGUAGE_NOT_FOUND(404, "Language not found"),
    PERMISSION_DENIED(403, "Permission denied"),
    CONTAINER_NOT_FOUND(404, "Container not found"),
    LANGUAGE_NOT_SUPPORTED(400, "Language not supported"),
    LANGUAGE_NOT_AVAILABLE(404, "Language not available"),;
    private int code;
    private String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
