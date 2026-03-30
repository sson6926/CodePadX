package com.shawnix.codepadx.dto.request.auth;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Size(max = 20, message = "Password must be less than 20 characters long")
    String password;
}
