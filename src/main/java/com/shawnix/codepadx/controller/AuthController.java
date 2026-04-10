package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.auth.LoginRequest;
import com.shawnix.codepadx.dto.request.user.CreateUserRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.auth.LoginResponse;
import com.shawnix.codepadx.dto.response.user.CreateUserResponse;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.security.JwtUtil;
import com.shawnix.codepadx.service.AuthService;
import com.shawnix.codepadx.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {

        return ApiResponse.<LoginResponse>builder()
                .message("Login success")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/signup")
    public ApiResponse<CreateUserResponse> signup(@Valid @RequestBody CreateUserRequest request) {
        return ApiResponse.<CreateUserResponse>builder()
                .message("Signup success")
                .data(userService.createUser(request))
                .build();
    }




}
