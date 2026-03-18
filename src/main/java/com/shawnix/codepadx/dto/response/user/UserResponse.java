package com.shawnix.codepadx.dto.response.user;

import com.shawnix.codepadx.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserResponse {
    private int id;
    private String name;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}