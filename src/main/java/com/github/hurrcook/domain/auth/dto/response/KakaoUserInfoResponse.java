package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse (
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount

) {
    public record KakaoAccount(
            Profile profile

    ) {
        public record Profile(
                String nickname
        ){}
    }
}
