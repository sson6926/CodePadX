package com.shawnix.codepadx.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
    private String name;
    @Size(min = 6, message = "Username toi thieu 6 ki tu")
    @Size(max = 15, message = "Username ko qua 15 ki tu")
    private String username;
    @Size(min = 6, message = "Password toi thieu 6 ki tu")
    private String password;
    @Email
    private String email;
}

