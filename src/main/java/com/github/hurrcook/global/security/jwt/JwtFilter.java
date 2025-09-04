package com.github.hurrcook.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hurrcook.domain.auth.exception.AuthExceptions;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.exception.UserExceptions;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.exception.ApiException;
import com.github.hurrcook.global.response.ApiResponse;
import com.github.hurrcook.global.security.authentication.UserAuthentication;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String accessToken = jwtUtil.extractToken(request);

        try{
            if (jwtUtil.validateToken(accessToken)) {

                if (jwtUtil.extractTypeOfToken(accessToken).equals("RefreshToken")) {
                    throw AuthExceptions.REFRESH_TOKEN_NOT_ALLOWED.toApiException();
                }

                // 요청 시 black 처리된 액세스 토큰인지 확인
                String key = "BLACKED_TOKEN" + accessToken;
                if (redisTemplate.hasKey(key)) {
                    throw AuthExceptions.INVALID_TOKEN.toApiException();
                }

                UUID id = jwtUtil.extractIdFromToken(accessToken);
                User user = userRepository.findById(id)
                        .orElseThrow(UserExceptions.USER_NOT_FOUND::toApiException);

                UserAuthentication authentication = new UserAuthentication(user);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }catch (ApiException e){

            ApiResponse<?> apiResponse = ApiResponse.error(e);

            String body = objectMapper.writeValueAsString(apiResponse);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(body);

            return;
        }

        filterChain.doFilter(request,response);


    }
}
