package com.shawnix.codepadx.dto.request.auth;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
