package com.github.hurrcook.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${oauth.kakao.client-id}")
    private String clientId;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    private final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    public String getKakaoLoginUrl(){ // 로그인 페이지로의 리다이렉트 Url 반환
        return UriComponentsBuilder.fromHttpUrl(KAKAO_AUTH_URL)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .toUriString();
    }
}
