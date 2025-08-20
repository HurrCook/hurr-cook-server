package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TokenResponse {
    @JsonProperty("access_token") private String accessToken;

    @JsonProperty("refresh_token") private String refreshToken;
}
