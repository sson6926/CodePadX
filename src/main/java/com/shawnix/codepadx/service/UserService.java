package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.user.CreateUserRequest;
import com.shawnix.codepadx.dto.response.user.CreateUserResponse;
import com.shawnix.codepadx.dto.response.user.UserResponse;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.entity.enums.Role;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        } else {
            User user = User.builder()
                    .name(request.getName())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .role(Role.USER)
                    .build();
            User createdUser = userRepository.save(user);
            return CreateUserResponse.builder()
                    .id(createdUser.getId())
                    .name(createdUser.getName())
                    .username(createdUser.getUsername())
                    .password(createdUser.getPassword())
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
                        .email(u.getEmail()).build()
        ).toList();

    }
}
