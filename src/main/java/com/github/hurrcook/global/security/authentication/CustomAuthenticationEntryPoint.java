package com.github.hurrcook.global.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hurrcook.domain.auth.exception.AuthExceptions;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 인증 실패 시 예외 처리
        ApiResponse<?> apiResponse = ApiResponse.error(AuthExceptions.AUTHENTICATION_FAILED.toApiException());

        String body = objectMapper.writeValueAsString(apiResponse);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);

    }
}
