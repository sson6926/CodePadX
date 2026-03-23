package com.shawnix.codepadx.dto.response.auth;

import com.shawnix.codepadx.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginResponse {
    private String token;
    private Long userId;
    private String name;
    private String username;
    private String email;
    private Role role;
}
