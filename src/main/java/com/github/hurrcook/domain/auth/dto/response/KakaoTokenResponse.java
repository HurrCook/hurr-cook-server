package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class KakaoTokenResponse{
    @JsonProperty("token_type") String tokenType;
    @JsonProperty("access_token") String accessToken;
    @JsonProperty("expires_in") Integer expiresIn; // 액세스 토큰 ttl; 단위 seconds
    @JsonProperty("refresh_token") String refreshToken;
    @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn;
    String scope;
}
