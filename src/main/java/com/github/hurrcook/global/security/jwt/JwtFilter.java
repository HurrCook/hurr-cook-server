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


    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String accessToken = jwtUtil.extractToken(request);

        try{
            if (jwtUtil.validateToken(accessToken)) {

                UUID id = jwtUtil.extractIdFromToken(accessToken);
                User user = userRepository.findById(id)
                        .orElseThrow(UserExceptions.USER_NOT_FOUND::toApiException);

                UserAuthentication authentication = new UserAuthentication(user);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{

                throw AuthExceptions.AUTHENTICATION_FAILED.toApiException();
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
