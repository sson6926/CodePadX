package com.shawnix.codepadx.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(401, "User existed"),
    CODE_NOT_FOUND(404, "Code not found"),
    LANGUAGE_NOT_FOUND(404, "Language not found");


    private int code;
    private String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
