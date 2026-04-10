package com.shawnix.codepadx.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RefreshResponse {
    String accessToken;
    String refreshToken;
}
