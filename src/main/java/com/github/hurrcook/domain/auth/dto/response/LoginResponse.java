package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class LoginResponse {
    @JsonProperty("userId") private UUID userId;

    @JsonProperty("name") private String name;

    @JsonProperty("accessToken") private String accessToken;

    @JsonProperty("refreshToken") private String refreshToken;

    @JsonProperty("firstLogin") private boolean firstLogin;
}