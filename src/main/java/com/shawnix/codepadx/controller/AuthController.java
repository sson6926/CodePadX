package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.auth.LoginRequest;
import com.shawnix.codepadx.dto.request.user.CreateUserRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.auth.LoginResponse;
import com.shawnix.codepadx.dto.response.user.CreateUserResponse;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.security.JwtUtil;
import com.shawnix.codepadx.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return ApiResponse.<LoginResponse>builder()
                .message("Login success")
                .data(response)
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
