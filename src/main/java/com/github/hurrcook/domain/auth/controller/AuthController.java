package com.github.hurrcook.domain.auth.controller;

import com.github.hurrcook.domain.auth.dto.response.TokenResponse;
import com.github.hurrcook.domain.auth.service.AuthService;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    // client에서 로그인 요청 -> 로그인 페이지로 리다이렉트
    @GetMapping("/kakao/login")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(authService.getKakaoLoginUrl());
//        return ApiResponse.ok();
    }

    @GetMapping("/kakao/callback") // 사용자 인증 후 쿼리 파라미터에 인가 코드를 담아 callback 되어 토큰 발급 요청
    public ApiResponse<TokenResponse> callback(@RequestParam("code") @NotBlank String authorizeCode) {
        return ApiResponse.ok(authService.kakaoLogin(authorizeCode));
    }
}
