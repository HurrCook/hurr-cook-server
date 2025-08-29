package com.github.hurrcook.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.hurrcook.domain.auth.entity.RefreshToken;
import com.github.hurrcook.domain.auth.exception.AuthExceptions;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.RefreshTokenRedisRepository;
import com.github.hurrcook.global.property.JwtProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.auth0.jwt.algorithms.Algorithm;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JwtProperty jwtProperty;

    @Bean
    public Algorithm algorithm(){

        return Algorithm.HMAC256(jwtProperty.getSecretKey());
    }

    public String createAccessToken(User user){
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(jwtProperty.getAccessExpiration(), ChronoUnit.HOURS))
                .withClaim("id",user.getId().toString())
                .sign(algorithm());
    }

    public String createRefreshToken(User user){
        String token =  JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(jwtProperty.getRefreshExpiration(), ChronoUnit.HOURS))
                .withClaim("id",user.getId().toString()) // baseSchema Id
                .sign(algorithm());

        // refreshToken 엔티티 생성 및 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId().toString()) // 토큰에 baseSchema Id로 저장
                .refreshToken(token)
                .ttl(jwtProperty.getRefreshExpiration())
                .build();

        refreshTokenRedisRepository.save(refreshToken);

        return token;
    }


    public String extractToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")){

            return authorization.substring(7);

        }else return null;
    }

    public boolean validateToken(String token){

        if (Objects.isNull(token)) return false;

        try{

            JWT.require(algorithm()).build().verify(token);
            return true;

        }catch (TokenExpiredException e){

            throw AuthExceptions.ACCESS_TOKEN_EXPIRED.toApiException();

        }catch(Exception e){

            return false;

        }
    }

    public UUID extractIdFromToken(String token){

        return JWT.decode(token).getClaim("id").as(UUID.class);
    }
}
