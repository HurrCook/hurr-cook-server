package com.github.hurrcook.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String extractToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }else return null;
    }
}
