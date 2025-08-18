package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record KakaoTokenResponseDto (
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") Integer expiresIn, // 액세스 토큰 ttl, 단위 seconds
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn
) {
    public static KakaoTokenResponseDto from(String tokenType, String accessToken, Integer expiresIn, String refreshToken, Integer refreshTokenExpiresIn){
        return KakaoTokenResponseDto.builder()
                .tokenType(tokenType)
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }
}
