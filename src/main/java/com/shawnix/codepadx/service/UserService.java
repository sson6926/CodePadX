package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.user.CreateUserRequest;
import com.shawnix.codepadx.dto.response.user.CreateUserResponse;
import com.shawnix.codepadx.dto.response.user.UserResponse;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.entity.enums.Role;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        } else {
            User user = User.builder()
                    .name(request.getName())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            User createdUser = userRepository.save(user);
            return CreateUserResponse.builder()
                    .id(createdUser.getId())
                    .name(createdUser.getName())
                    .username(createdUser.getUsername())
                    .email(createdUser.getEmail())
                    .build();
        }
    }
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(
                u -> UserResponse.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .name(u.getName())
                        .email(u.getEmail())
                        .role(u.getRole())
                        .createdAt(u.getCreatedAt())
                        .build()
        ).toList();

    }
}
