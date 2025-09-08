package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TokenResponse {

    @JsonProperty("accessToken") private String accessToken;

    @JsonProperty("refreshToken")private String refreshToken;
}
