package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.request.auth.LoginRequest;
import com.shawnix.codepadx.dto.request.auth.RefreshRequest;
import com.shawnix.codepadx.dto.response.auth.LoginResponse;
import com.shawnix.codepadx.dto.response.auth.RefreshResponse;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.security.CustomUserDetailsService;
import com.shawnix.codepadx.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public RefreshResponse refresh(RefreshRequest request) {
        Long userId = jwtUtil.extractUserIdWithRefreshToken(request.getRefreshToken());
        System.out.println(userId);
        UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
        if(!jwtUtil.validateRefreshToken(request.getRefreshToken(), userDetails)) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String newAccessToken = jwtUtil.generateAccessToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

        return RefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
