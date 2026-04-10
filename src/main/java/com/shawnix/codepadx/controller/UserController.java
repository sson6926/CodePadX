package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.user.CreateUserRequest;
import com.shawnix.codepadx.dto.response.user.UserResponse;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.user.CreateUserResponse;
import com.shawnix.codepadx.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    ApiResponse<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ApiResponse.<CreateUserResponse>builder()
                .message("OK")
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUser())
                .message("Get all user ok")
                .build();
    }
}
