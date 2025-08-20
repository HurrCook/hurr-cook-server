package com.github.hurrcook.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class KakaoUserInfoResponse {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class KakaoAccount {
        private Profile profile;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class Profile {
        private String nickname;
    }
}
