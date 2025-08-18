package com.github.hurrcook.domain.auth.controller;

import com.github.hurrcook.domain.auth.service.AuthService;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // client에서 로그인 요청 -> 로그인 페이지로 리다이렉트
    @GetMapping("/kakao/login")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(authService.getKakaoLoginUrl());
//        return ApiResponse.ok();
    }

}
