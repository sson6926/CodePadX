package com.shawnix.codepadx.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateUserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
}
