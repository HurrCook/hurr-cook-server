package com.github.hurrcook.domain.auth.controller;

import com.github.hurrcook.domain.auth.dto.request.FcmTokenRequest;
import com.github.hurrcook.domain.auth.dto.request.RefreshTokenRequest;
import com.github.hurrcook.domain.auth.dto.response.LoginResponse;
import com.github.hurrcook.domain.auth.dto.response.TokenResponse;
import com.github.hurrcook.domain.auth.service.AuthService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import com.github.hurrcook.global.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "인증")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // client에서 로그인 요청 -> 로그인 페이지로 리다이렉트
    @GetMapping("/kakao/login")
    @Operation(summary = "로그인 요청")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(authService.getKakaoLoginUrl());
    }

    @GetMapping("/kakao/callback") // 사용자 인증 후 쿼리 파라미터에 인가 코드를 담아 callback 되어 토큰 발급 요청
    @Operation(summary = "토큰 발급 요청")
    public ApiResponse<LoginResponse> callback(@RequestParam("code") @NotBlank String authorizeCode) {
        return ApiResponse.ok(authService.kakaoLogin(authorizeCode));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자 세션 종료: 리프레시 토큰 삭제, 액세스 토큰 블랙리스트 처리")
    public ApiResponse<Void> logout( HttpServletRequest request, @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) { // 현재 액세스 토큰으로 요청

        String accessToken = jwtUtil.extractToken(request);
        authService.logout(accessToken,refreshTokenRequest.getRefreshToken());
        return ApiResponse.ok();
    }

    @PostMapping("/reissuance")
    @Operation(summary = "토큰 재발급")
    public ApiResponse<TokenResponse> reissuance(HttpServletRequest request, @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

        String accessToken = jwtUtil.extractToken(request);

        return ApiResponse.ok(authService.regenerateToken(accessToken,refreshTokenRequest.getRefreshToken()));
    }

    @PostMapping("fcm")
    @Operation(summary = "FCM 토큰 등록")
    public ApiResponse<Void> uploadFcm (@AuthenticationPrincipal User user, @Valid @RequestBody FcmTokenRequest  fcmTokenRequest) {

        authService.uploadFcm(user,fcmTokenRequest);
        return ApiResponse.ok();
    }
}
